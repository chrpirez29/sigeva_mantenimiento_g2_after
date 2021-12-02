package com.equipo3.SIGEVA.controller;

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.exception.CentroInvalidoException;
import com.equipo3.SIGEVA.exception.IdentificadorException;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.Usuario;
import com.equipo3.SIGEVA.utils.Utilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/***
 * 
 * Controlador (RestController) donde tendremos todos los recursos web relativos
 * a la gestión de los centros de salud.
 * 
 * @author Equipo3
 *
 */
@CrossOrigin
@RestController
@RequestMapping("centro")
public class CentroController {

	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private CupoDao cupoDao;
	@Autowired
	private CentroSaludDao centroSaludDao;
	@Autowired
	private WrapperModelToDTO wrapperModelToDTO;
	@Autowired
	private Utilidades utilidades;
	@Autowired
	private WrapperDTOtoModel wrapperDTOtoModel = new WrapperDTOtoModel();

	/**
	 * Recurso web para la creación de un nuevo centro de salud en el sistema.
	 *
	 * @param centroSaludDTO Centro de salud que viene del front end y que se crea a
	 *                       partir de los datos proporcionados por el administrador
	 *                       (usuario).
	 */
	@PostMapping("/newCentroSalud")
	public String crearCentroSalud(@RequestBody CentroSaludDTO centroSaludDTO) {
		try {
			centroSaludDTO.setVacuna(utilidades.getVacunaByNombre("Pfizer"));
			CentroSalud centroSalud = this.wrapperDTOtoModel.centroSaludDTOtoCentroSalud(centroSaludDTO);
			Optional<CentroSalud> optCentroSalud = centroSaludDao.findByNombreCentro(centroSalud.getNombreCentro());
			if (optCentroSalud.isPresent()) {
				throw new CentroInvalidoException("El centro de salud ya existe en la base de datos");
			}
			centroSaludDao.save(centroSalud);

			return centroSalud.getId();

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Recurso web para la eliminación de un centro de salud registrador en el
	 * sistema.
	 *
	 * @param centroSaludDTO Centro de salud que se quiere eliminar y que viene del
	 *                       front end.
	 */
	@PostMapping("/deleteCentroSalud")
	public void borrarCentroSalud(@RequestBody CentroSaludDTO centroSaludDTO) {
		try {
			CentroSalud centroSalud = this.wrapperDTOtoModel.centroSaludDTOtoCentroSalud(centroSaludDTO);
			Optional<CentroSalud> optCentroSalud = centroSaludDao.findById(centroSalud.getId());
			if (optCentroSalud.isPresent()) {
				if (cupoDao.buscarCuposOcupados(centroSalud.getId(), new Date()).isEmpty()) {
					if (usuarioDao.findAllByCentroSalud(centroSalud.getId()).isEmpty()) {
						centroSaludDao.deleteById(centroSalud.getId());
					} else {
						throw new CentroInvalidoException(
								"El centro de salud NO se puede borrar por contener usuarios.");
					}

				} else {
					throw new CentroInvalidoException("El centro de salud NO se puede borrar por contener citas.");
				}
			} else {
				throw new CentroInvalidoException("El centro de salud NO existe.");
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Recurso web para la modificación de los centros de salud por parte de los
	 * administradores.
	 *
	 * @param csDto Centro de salud modificado
	 */
	@PostMapping("/updateCS")
	public void modificarCentroSalud(@RequestBody CentroSaludDTO csDto) {
		try {

			Optional<CentroSalud> optCentro = centroSaludDao.findById(csDto.getId());
			if (!optCentro.isPresent()) {
				throw new CentroInvalidoException("El centro de salud no existe");
			}
			centroSaludDao.save(wrapperDTOtoModel.centroSaludDTOtoCentroSalud(csDto));

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	/***
	 * Recurso web para la obtención del centro de salud que tiene asignado un
	 * usuario del tipo sanitario.
	 * 
	 * @param idUsuario Identificador del usuario, del cual se quiere obtener el
	 *                  centro.
	 * @return CentroSaludDTO centro salud al que esta asociado el sanitario.
	 */
	@GetMapping("/getCentroSanitario")
	public CentroSaludDTO getCentroSanitario(@RequestParam String idUsuario) {
		try {
			Optional<Usuario> optUsuario = this.usuarioDao.findById(idUsuario);
			if (!optUsuario.isPresent()) {
				throw new UsuarioInvalidoException("Usuario no existe en el sistema");
			}
			Usuario usuario = optUsuario.get();

			Optional<CentroSalud> optionalCentroSalud = centroSaludDao.findById(usuario.getCentroSalud());
			if (!optionalCentroSalud.isPresent()) {
				throw new CentroInvalidoException("El centro de salud no existe");
			}

			return this.wrapperModelToDTO.centroSaludToCentroSaludDTO(optionalCentroSalud.get());

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Recurso para la obtención de un centro de salud a partir de su identificador.
	 *
	 * @param idCentroSalud Identificador del centro de salud, el cual queremos
	 *                      obtener de la bbdd.
	 * @return CentroSaludDTO Centro de salud obtenido de la bbdd.
	 */
	@GetMapping("/getCentroSaludById")
	public CentroSaludDTO getCentroById(@RequestParam String idCentroSalud) {
		try {
			Optional<CentroSalud> optCentroSalud = centroSaludDao.findById(idCentroSalud);
			if (optCentroSalud.isPresent()) {
				return wrapperModelToDTO.centroSaludToCentroSaludDTO(optCentroSalud.get());
			}
			throw new IdentificadorException("Identificador Centro Salud " + idCentroSalud + " no existe");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Recurso web para la obtención de todos los centros de salud que tiene el
	 * sistema registrados.
	 *
	 * @return List<CentroSaludDTO> Lista que contiene todos los centros de salud
	 *         que tiene el sistema.
	 */
	@GetMapping("/getCentros")
	public List<CentroSaludDTO> listarCentros() {
		return wrapperModelToDTO.allCentroSaludToCentroSaludDTO(centroSaludDao.findAll());
	}

	/**
	 * Recurso web para la modificación de la dosis disponibles que tiene un centro
	 * de salud.
	 *
	 * @param centroSalud Identificador del centro de salud de cual se quieren
	 *                    modificar las dosis de vacunas disponibles.
	 * @param vacunas     Número de vacunas que se quieren añadir a las disponibles
	 *                    del centro de salud.S
	 */
	@PutMapping("/modificarDosisDisponibles/{centroSalud}/{vacunas}")
	public void modificarNumeroVacunasDisponibles(@PathVariable String centroSalud, @PathVariable int vacunas) {
		try {
			Optional<CentroSalud> optCentroSalud = centroSaludDao.findById(centroSalud);
			if (optCentroSalud.isPresent()) {
				CentroSaludDTO centroSaludDTO = wrapperModelToDTO.centroSaludToCentroSaludDTO(optCentroSalud.get());
				centroSaludDTO.incrementarNumVacunasDisponibles(vacunas);
				centroSaludDao.save(wrapperDTOtoModel.centroSaludDTOtoCentroSalud(centroSaludDTO));
			}

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
}
