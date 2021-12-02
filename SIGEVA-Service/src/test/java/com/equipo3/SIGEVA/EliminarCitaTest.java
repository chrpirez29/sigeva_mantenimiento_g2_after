package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.equipo3.SIGEVA.exception.CupoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.CentroController;
import com.equipo3.SIGEVA.controller.CitaController;
import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.controller.UsuarioController;
import com.equipo3.SIGEVA.dao.CitaDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.CitaDTO;
import com.equipo3.SIGEVA.dto.CupoDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.model.Cita;
import com.equipo3.SIGEVA.utils.Utilidades;

@SpringBootTest
class EliminarCitaTest {

	public static CentroSaludDTO centroSaludDTO;
	public static CupoDTO cupoDTO;
	public static PacienteDTO pacienteDTO;
	public static CitaDTO cita1DTO;
	public static CitaDTO cita2DTO;

	@Autowired
	private UsuarioController usuarioController;

	@Autowired
	private CentroController centroController;

	@Autowired
	private CupoController cupoController;

	@Autowired
	private CitaController citaController;

	@Autowired
	private CitaDao citaDao;

	@Autowired
	private Utilidades utilidades;

	@BeforeAll
	static void setUpCita() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());

		cupoDTO = new CupoDTO();
		cupoDTO.setCentroSalud(centroSaludDTO);

		pacienteDTO = new PacienteDTO();
		pacienteDTO.setCentroSalud(centroSaludDTO);
		pacienteDTO.setUsername(UUID.randomUUID().toString());

		cita1DTO = new CitaDTO();
		cita1DTO.setCupo(cupoDTO);
		cita1DTO.setPaciente(pacienteDTO);
		cita1DTO.setDosis(1);

		cita2DTO = new CitaDTO();
		cita2DTO.setCupo(cupoDTO);
		cita2DTO.setPaciente(pacienteDTO);
		cita2DTO.setDosis(2);
	}

	@SuppressWarnings("deprecation")
	@BeforeEach
	void antes() {
		centroSaludDTO.setNumVacunasDisponibles(50);
		pacienteDTO.setRol(utilidades.getRolByNombre("Paciente"));
		pacienteDTO.setNumDosisAplicadas(0);

		Date manana = new Date();
		manana.setDate(manana.getDate() + 1);
		cupoDTO.setFechaYHoraInicio(manana);

		centroController.crearCentroSalud(centroSaludDTO);
		usuarioController.crearUsuarioPaciente(pacienteDTO);
		cupoController.crearCupo(cupoDTO);
		citaDao.save(WrapperDTOtoModel.citaDTOToCita(cita1DTO));
		citaDao.save(WrapperDTOtoModel.citaDTOToCita(cita2DTO));
	}

	@AfterEach
	void despues() {
		citaController.eliminarTodasLasCitasDelPaciente(pacienteDTO);
		utilidades.eliminarUsuario(pacienteDTO.getUsername());
		cupoController.eliminarCupo(cupoDTO.getUuidCupo());
		utilidades.eliminarCentro(centroSaludDTO.getId());
	}

	@Test
	void eliminarTodasCitasDelPaciente() {
		List<CitaDTO> listaFuturas1 = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertTrue(listaFuturas1.size() > 0);

		citaController.eliminarTodasLasCitasDelPaciente(pacienteDTO);
		List<CitaDTO> listaAntiguas2 = citaController.obtenerCitasAntiguasPaciente(pacienteDTO);
		List<CitaDTO> listaFuturas2 = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertTrue(listaAntiguas2.size() == 0 && listaFuturas2.size() == 0);
	}

	@Test
	void eliminarCitasFuturasDelPaciente() throws CupoException {
		List<CitaDTO> listaFuturas1 = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertTrue(listaFuturas1.size() > 0);

		citaController.eliminarCitasFuturasDelPaciente(pacienteDTO);

		List<CitaDTO> listaFuturas2 = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertEquals(0, listaFuturas2.size());
	}

	@Test
	void eliminarTodasCitasDelCupo() {
		List<Cita> listaInicial = citaDao.findAllByUuidCupo(cupoDTO.getUuidCupo());
		assertTrue(listaInicial.size() > 0);

		citaController.eliminarTodasLasCitasDelCupo(cupoDTO.getUuidCupo());

		List<Cita> listaFinal = citaDao.findAllByUuidCupo(cupoDTO.getUuidCupo());
		assertEquals(0, listaFinal.size());
	}

	@Test
	void eliminarCitaInexistente() {
		try {
			citaController.eliminarCita("No existo.");
		} catch (Exception e) {
			assertNotNull(e); // Esperado.
		}
	}

}