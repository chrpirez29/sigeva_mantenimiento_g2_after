package com.equipo3.SIGEVA.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.CupoDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.exception.CupoException;
import com.equipo3.SIGEVA.exception.IdentificadorException;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.model.Cupo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.logging.Logger.getLogger;

@CrossOrigin
@RestController
@RequestMapping("cupo")
public class CupoController {

	@Autowired
	CupoDao cupoDao;

	@Autowired
	CentroSaludDao centroSaludDao;

	@Autowired
	UsuarioDao usuarioDao;

	@Autowired
	ConfiguracionCuposDao configuracionCuposDao;

	@Autowired
	CitaController citaController;

	@Autowired
	WrapperModelToDTO wrapperModelToDTO;

	@Autowired
	WrapperDTOtoModel wrapperDTOtoModel;

	final Logger LOG = getLogger(com.equipo3.SIGEVA.controller.CitaController.class.toString()) ;

	/**
	 * El método ayudará a calcular cuáles son exactamente los cupos de un centro en
	 * concreto, dada la configuración establecida de horarios.
	 * 
	 * @param centroSaludDTO
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<CupoDTO> calcularCupos(CentroSaludDTO centroSaludDTO) {
		// No requerirá tiempo de ejecución.

		List<CupoDTO> momentos = new ArrayList<>();

		ConfiguracionCupos configuracionCupos = configuracionCuposDao.findAll().get(0);

		Date fechaInicio = configuracionCupos.getFechaInicioAsDate();

		Date fechaFinAbsoluta = new Date(Condicionamientos.anyoFin() - 1900, Condicionamientos.mesFin() - 1,
				Condicionamientos.diaFin(), configuracionCupos.getHoraFin().getHours(),
				configuracionCupos.getHoraFin().getMinutes());

		int duracionTramo = configuracionCupos.getDuracionMinutos();

		Date fechaIterada = copia(fechaInicio);

		while (fechaIterada.before(fechaFinAbsoluta)) {

			Date fechaFinDiaria = copia(fechaIterada);
			fechaFinDiaria.setHours(fechaFinAbsoluta.getHours()); // Fin del día.
			fechaFinDiaria.setMinutes(fechaFinAbsoluta.getMinutes());

			while (fechaIterada.before(fechaFinDiaria)) {
				momentos.add(new CupoDTO(centroSaludDTO, copia(fechaIterada), 0));
				fechaIterada.setMinutes(fechaIterada.getMinutes() + duracionTramo);
			}
			fechaIterada.setDate(fechaIterada.getDate() + 1); // Cambio de día.

			fechaIterada.setHours(fechaInicio.getHours()); // Reinicio del día.
			fechaIterada.setMinutes(fechaInicio.getMinutes());

		}

		return momentos;
	}

	/**
	 * Basándose en el método de calcular los cupos, este método guardará
	 * (inicializará) dichos cupos en la base de datos. Tardará en ejecutarse.
	 * 
	 * @param uuidCentroSalud
	 * @return
	 */
	@SuppressWarnings("static-access")
	@GetMapping("/prepararCupos")
	public List<CupoDTO> prepararCupos(@RequestParam String uuidCentroSalud) {
		Optional<CentroSalud> optCentroSalud = centroSaludDao.findById(uuidCentroSalud);
		CentroSaludDTO centroSaludDTO = null;
		if (optCentroSalud.isPresent()) {
			centroSaludDTO = wrapperModelToDTO.centroSaludToCentroSaludDTO(optCentroSalud.get());
		}
		if (centroSaludDTO != null) {
			List<CupoDTO> cuposDTO = calcularCupos(centroSaludDTO);
			List<Cupo> cupos = wrapperDTOtoModel.allCupoDTOtoCupo(cuposDTO);
			for (int i = 0; i < cupos.size(); i++) {
				cupoDao.save(cupos.get(i));
			}
			return cuposDTO;
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Centro de salud no contemplado.");
		}
	}

	/**
	 * Este método buscará cuáles son todos los cupos libres a partir de una fecha
	 * en concreto (día inclusive, desde la hora especificada) situados en un
	 * centro, por los cuales, se podrán programar nuevas citas.
	 * 
	 * @param centroSaludDTO
	 * @param fecha
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<Cupo> buscarCuposLibresAPartirDeLaFecha(CentroSaludDTO centroSaludDTO, @RequestBody Date fecha) {
		// Este método se utiliza para buscar los próximos cupos libres (para asignar).
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		if (formateador.format(fecha).equals(formateador.format(new Date()))
				&& Condicionamientos.buscarAPartirDeManana()) {
			fecha.setDate(fecha.getDate() + 1);
			fecha.setHours(0);
			fecha.setMinutes(0);
			fecha.setSeconds(0);
		}

		List<Cupo> cupos = cupoDao.buscarCuposLibresAPartirDe(centroSaludDTO.getId(), fecha,
				configuracionCuposDao.findAll().get(0).getNumeroPacientes());
		Collections.sort(cupos);
		if (cupos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"¡No hay hueco disponible a partir de " + fecha + "!");
		}
		return cupos;
	}

	/**
	 * Este método devolverá cuál es el primer cupo (hueco) libre a partir de una
	 * determinada fecha (día y hora) en un centro en concreto. Este método se
	 * utiliza para buscar el próximo cupo libre (para asignar una nueva cita).
	 * 
	 * @param centroSaludDTO
	 * @param aPartirDeLaFecha
	 * @return
	 */
	public Cupo buscarPrimerCupoLibreAPartirDe(CentroSaludDTO centroSaludDTO, Date aPartirDeLaFecha) {
		return buscarCuposLibresAPartirDeLaFecha(centroSaludDTO, aPartirDeLaFecha).get(0);
		// Lanzará exception en caso de no haber hueco.
	}

	/**
	 * El método ayudará a buscar los cupos libres de exactamente una fecha en
	 * concreto. Se utilizará para buscar los cupos libres del día (para modificar).
	 * La hora de la fecha no importa, sino solamente el día.
	 * 
	 * @param uuidPaciente
	 * @param fechaJson
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@GetMapping("/buscarCuposLibresFecha")
	public List<CupoDTO> buscarCuposLibresFechaSJR(@RequestParam(name = "uuidPaciente") String uuidPaciente,
			@RequestParam(name = "fecha") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") String fechaJson) {

		ObjectMapper mapper = new ObjectMapper();
		Date fecha = null;

		try {
			fecha = mapper.readValue(fechaJson, Date.class);
		} catch (JsonProcessingException j) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de fecha inválido");
		}

		if (uuidPaciente != null) {
			PacienteDTO pacienteDTO = null;
			try {
				pacienteDTO = wrapperModelToDTO.getPacienteDTOfromUuid(uuidPaciente);
			} catch (IdentificadorException e) {
				e.printStackTrace();
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado en BD.");
			}
			Date fechaInicio = CupoController.copia(fecha);
			fechaInicio.setHours(0);
			fechaInicio.setMinutes(0);
			Date fechaFin = CupoController.copia(fechaInicio);
			fechaFin.setDate(fechaFin.getDate() + 1);
			List<CupoDTO> cuposDTO = wrapperModelToDTO
					.allCupoToCupoDTO(cupoDao.buscarCuposLibresDelTramo(pacienteDTO.getCentroSalud().getId(),
							fechaInicio, fechaFin, configuracionCuposDao.findAll().get(0).getNumeroPacientes()));
			Collections.sort(cuposDTO);
			if (cuposDTO.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						"¡No hay hueco disponible en este día (" + fecha + ")!");
			}
			return cuposDTO;
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "UUID de paciente no contemplado.");
		}
	}

	/**
	 * Método usado para obtener TODOS los cupos de ese centro de exactamente ese
	 * día. El método ayuda al método de buscar las citas del día (para vacunar).
	 * 
	 * @param centroSaludDTO
	 * @param fecha
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<CupoDTO> buscarTodosCuposFecha(CentroSaludDTO centroSaludDTO, Date fecha) {
		Date fechaInicio = CupoController.copia(fecha);
		fechaInicio.setHours(0);
		fechaInicio.setMinutes(0);
		Date fechaFin = CupoController.copia(fechaInicio);
		fechaFin.setDate(fechaFin.getDate() + 1);
		List<Cupo> cupos = cupoDao.buscarTodosCuposDelTramo(centroSaludDTO.getId(), fechaInicio, fechaFin);
		List<CupoDTO> cuposDTO = wrapperModelToDTO.allCupoToCupoDTO(cupos);
		Collections.sort(cuposDTO);
		return cuposDTO;
	}

	/**
	 * Este método ayuda a incrementar el tamaño actual de un cupo en concreto, para
	 * contabilizar hasta el máximo establecido en la configuración.
	 * 
	 * @param uuidCupo
	 * @throws CupoException
	 */
	@SuppressWarnings("static-access")
	public void incrementarTamanoActual(String uuidCupo) throws CupoException {
		Optional<Cupo> optCupo = cupoDao.findById(uuidCupo);
		if (optCupo.isPresent()) {
			CupoDTO cupoDTO = wrapperModelToDTO.cupoToCupoDTO(optCupo.get());

			// Lanza excepción si ya está lleno.
			cupoDTO.incrementarTamanoActual(configuracionCuposDao.findAll().get(0).getNumeroPacientes());
			cupoDao.save(wrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
		} else {
			throw new CupoException("Cupo no identificado en la base de datos");
		}
	}

	/**
	 * Este método ayuda a decrementar el tamaño actual de un cupo en concreto, para
	 * contabilizar hasta el máximo establecido en la configuración.
	 * 
	 * @param uuidCupo
	 * @throws CupoException
	 */
	@SuppressWarnings("static-access")
	public void decrementarTamanoActualCupo(String uuidCupo) throws CupoException {
		Optional<Cupo> optCupo = cupoDao.findById(uuidCupo);
		if (optCupo.isPresent()) {
			CupoDTO cupoDTO = wrapperModelToDTO.cupoToCupoDTO(optCupo.get());

			// Lanza excepción si ya está vacío.
			cupoDTO.decrementarTamanoActual();
			cupoDao.save(wrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
		} else {
			throw new CupoException("Cupo no identificado en la base de datos.");
		}
	}

	/**
	 * Este método ayuda a anular (equivaler a 0) el tamaño actual de un cupo en
	 * concreto. Se invocará al método cuando se vaya a querer borrar el cupo.
	 * 
	 * @param uuidCupo
	 * @throws CupoException
	 */
	@SuppressWarnings("static-access")
	public void anularTamanoActual(String uuidCupo) throws CupoException {
		Optional<Cupo> optCupo = cupoDao.findById(uuidCupo);
		if (optCupo.isPresent()) {
			CupoDTO cupoDTO = wrapperModelToDTO.cupoToCupoDTO(optCupo.get());
			cupoDTO.setTamanoActual(0);
			cupoDao.save(wrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
		} else {
			throw new CupoException("Cupo no identificado en la base de datos.");
		}
	}

	/**
	 * El método ayuda a eliminar un cupo en concreto de la base de datos.
	 * 
	 * @param uuidCupo
	 */
	public void eliminarCupo(String uuidCupo) {
		citaController.eliminarTodasLasCitasDelCupo(uuidCupo);
		cupoDao.deleteById(uuidCupo);
		/*
		 * El orden de estas líneas es necesario que sea así, porque cuando se elimina
		 * una cita se decrementa el tamaño del cupo, y si no existe provocaría
		 * NullPointers que no tienen por qué ser necesarias corregir si esto se ordena
		 * correctamente.
		 */
	}

	/**
	 * El método ayuda a eliminar todos los cupos de un centro. Tardará en
	 * ejecutarse.
	 * 
	 * @param centroSaludDTO
	 */
	@PutMapping("/borrarCuposDelCentro")
	public void borrarCuposDelCentro(@RequestBody CentroSaludDTO centroSaludDTO){
		List<Cupo> cupos = cupoDao.findAllByUuidCentroSalud(centroSaludDTO.getId());
		for (int i = 0; i < cupos.size(); i++) {
			this.eliminarCupo(cupos.get(i).getUuidCupo());
		}
	}

	/**
	 * El método ayuda a crear una instancia gemela de una fecha en concreto. Ayuda
	 * a desacoplar el paso por referencia de la primera fecha.
	 * 
	 * @param fecha
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date copia(Date fecha) { // Desacoplaje del Paso por Referencia. .
		return new Date(fecha.getYear(), fecha.getMonth(), fecha.getDate(), fecha.getHours(), fecha.getMinutes(),
				fecha.getSeconds());
	}

	/**
	 * Este método ayuda a crear un nuevo cupo sobre la base de datos, especificado
	 * por parámetro.
	 * 
	 * @param cupo
	 */
	public void crearCupo(CupoDTO cupo) {
			cupoDao.save(WrapperDTOtoModel.cupoDTOToCupo(cupo));
	}
}