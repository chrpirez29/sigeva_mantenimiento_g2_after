package com.equipo3.SIGEVA;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.equipo3.SIGEVA.controller.CentroController;
import com.equipo3.SIGEVA.utils.Utilidades;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.UsuarioController;
import com.equipo3.SIGEVA.controller.CitaController;
import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.dao.CitaDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.CitaDTO;
import com.equipo3.SIGEVA.dto.CupoDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.RolDTO;
import com.equipo3.SIGEVA.model.Cita;

@SpringBootTest
class ModificarCitaTest {
	@Autowired
	CitaController citaController = new CitaController();

	@Autowired
	UsuarioController usuarioController = new UsuarioController();

	@Autowired
	CentroController centroController;

	@Autowired
	CupoController cupoController = new CupoController();

	@Autowired
	private Utilidades utilidades;

	private static CupoDTO cupoDTO;
	private static CitaDTO citaDTO;
	private static PacienteDTO pacienteDTO;
	private static CentroSaludDTO centroSaludDTO;

	@Autowired
	CitaDao citaDao;

	@BeforeAll
	static void inicializacionObjetos() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());

		pacienteDTO = new PacienteDTO();
		pacienteDTO.setRol(new RolDTO());
		pacienteDTO.setCentroSalud(
				new CentroSaludDTO(UUID.randomUUID().toString(), "dirección", (int) Math.random() * 100));
		pacienteDTO.setUsername(UUID.randomUUID().toString());
		pacienteDTO.setCorreo("micorreo@correo.com");
		pacienteDTO.setHashPassword("sdfsdf");
		pacienteDTO.setDni("99999999Q");
		pacienteDTO.setNombre("Juan");
		pacienteDTO.setApellidos("Perez");
		pacienteDTO.setFechaNacimiento(new Date());
		pacienteDTO.setImagen("912imagen");

		cupoDTO = new CupoDTO();
		cupoDTO.setCentroSalud(centroSaludDTO);

		citaDTO = new CitaDTO();
		citaDTO.setPaciente(pacienteDTO);
		citaDTO.setCupo(cupoDTO);
		cupoDTO.setCentroSalud(centroSaludDTO);
	}

	@Test
	void modificacionCitaNoExistente() {
		try {
			citaController.modificarCita(citaDTO.getUuidCita(), cupoDTO.getUuidCupo());

		} catch (Exception e) {
			Assertions.assertEquals("204 NO_CONTENT \"La cita que se intenta modificar no existe\"", e.getMessage());
		}
	}

	@Test
	void modificacionCitaCupoNoExistente() {
		try {
			pacienteDTO.setRol(utilidades.getRolByNombre("Paciente"));
			centroController.crearCentroSalud(centroSaludDTO);
			usuarioController.crearUsuarioPaciente(pacienteDTO);
			cupoController.crearCupo(cupoDTO);
			citaController.crearCita(citaDTO);

			citaController.modificarCita(citaDTO.getUuidCita(), UUID.randomUUID().toString());
		} catch (Exception e) {
			Assertions.assertEquals("204 NO_CONTENT \"El cupo no existe\"", e.getMessage());
			citaDao.deleteById(citaDTO.getUuidCita());
			cupoController.eliminarCupo(cupoDTO.getUuidCupo());
			utilidades.eliminarCentro(centroSaludDTO.getId());
			utilidades.eliminarUsuario(pacienteDTO.getUsername());
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	void modificacionCitaCorrecta() {
		try {
			pacienteDTO.setRol(utilidades.getRolByNombre("Paciente"));
			centroController.crearCentroSalud(centroSaludDTO);
			usuarioController.crearUsuarioPaciente(pacienteDTO);

			CupoDTO newCupo = new CupoDTO();
			newCupo.setCentroSalud(centroSaludDTO);
			Date fecha = new Date();
			fecha.setDate(fecha.getDate() + 1);
			newCupo.setFechaYHoraInicio(fecha);

			cupoController.crearCupo(cupoDTO);
			cupoController.crearCupo(newCupo);
			citaController.crearCita(citaDTO);

			citaController.modificarCita(citaDTO.getUuidCita(), newCupo.getUuidCupo());

			Optional<Cita> citaEncontrado = citaDao.findById(citaDTO.getUuidCita());
			Cita citaBbdd = null;
			if (citaEncontrado.isPresent()) {
				citaBbdd = citaEncontrado.get();
			}

			Assertions.assertEquals(citaBbdd.getUuidCupo(), newCupo.getUuidCupo());

			citaDao.deleteById(citaDTO.getUuidCita());
			cupoController.eliminarCupo(cupoDTO.getUuidCupo());
			cupoController.eliminarCupo(newCupo.getUuidCupo());
			utilidades.eliminarCentro(centroSaludDTO.getId());
			utilidades.eliminarUsuario(pacienteDTO.getUsername());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
