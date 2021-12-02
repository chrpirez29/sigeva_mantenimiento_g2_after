package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.equipo3.SIGEVA.controller.CentroController;
import com.equipo3.SIGEVA.utils.Utilidades;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.controller.UsuarioController;
import com.equipo3.SIGEVA.controller.CitaController;
import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.CitaDTO;
import com.equipo3.SIGEVA.dto.CupoDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;

@SpringBootTest
class AsignarCitaTest {

	public static CentroSaludDTO centroSaludDTO;
	public static CupoDTO cupo1DTO;
	public static CupoDTO cupo2DTO;
	public static CupoDTO cupoAntiguoDTO;
	public static PacienteDTO pacienteDTO;

	@Autowired
	private UsuarioController usuarioController;

	@Autowired
	private CentroController centroController;

	@Autowired
	private CupoController cupoController;

	@Autowired
	private CitaController citaController;

	@Autowired
	private Utilidades utilidades;

	@SuppressWarnings("deprecation")
	@BeforeAll
	static void setUpCita() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		centroSaludDTO.setNumVacunasDisponibles(50);

		cupo1DTO = new CupoDTO();
		cupo1DTO.setCentroSalud(centroSaludDTO);
		Date manana = new Date();
		manana.setDate(manana.getDate() + 1);
		cupo1DTO.setFechaYHoraInicio(manana);

		cupo2DTO = new CupoDTO();
		cupo2DTO.setCentroSalud(centroSaludDTO);
		Date siguienteFecha = (Date) manana.clone();
		siguienteFecha.setDate(siguienteFecha.getDate() + 21);
		cupo2DTO.setFechaYHoraInicio(siguienteFecha);

		cupoAntiguoDTO = new CupoDTO();
		cupoAntiguoDTO.setCentroSalud(centroSaludDTO);
		Date anteriorFecha = (Date) manana.clone();
		anteriorFecha.setDate(anteriorFecha.getDate() - 21);
		cupoAntiguoDTO.setFechaYHoraInicio(anteriorFecha);

		pacienteDTO = new PacienteDTO();
		pacienteDTO.setCentroSalud(centroSaludDTO);
		pacienteDTO.setUsername(UUID.randomUUID().toString());
	}

	@BeforeEach
	void antes() {
		pacienteDTO.setRol(utilidades.getRolByNombre("Paciente"));
		centroController.crearCentroSalud(centroSaludDTO);
		cupoController.crearCupo(cupo1DTO);
		cupoController.crearCupo(cupo2DTO);
		cupoController.crearCupo(cupoAntiguoDTO);
	}

	@AfterEach
	void despues() {
		citaController.eliminarTodasLasCitasDelPaciente(pacienteDTO);
		utilidades.eliminarUsuario(pacienteDTO.getUsername());
		cupoController.eliminarCupo(cupo1DTO.getUuidCupo());
		cupoController.eliminarCupo(cupo2DTO.getUuidCupo());
		cupoController.eliminarCupo(cupoAntiguoDTO.getUuidCupo());
		utilidades.eliminarCentro(centroSaludDTO.getId());
	}

	@Test
	void asignarCitasPacienteCon0Dosis0CitasFuturas() {
		pacienteDTO.setNumDosisAplicadas(0);
		usuarioController.crearUsuarioPaciente(pacienteDTO);
		citaController.eliminarTodasLasCitasDelPaciente(pacienteDTO);

		List<CitaDTO> listaInicial = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertEquals(0, listaInicial.size());

		citaController.buscarYAsignarCitas(pacienteDTO.getIdUsuario());

		List<CitaDTO> listaFinal = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertEquals(2, listaFinal.size());
	}

	@Test
	void asignarCitasPacienteCon0Dosis1CitaFutura() {
		pacienteDTO.setNumDosisAplicadas(0);
		usuarioController.crearUsuarioPaciente(pacienteDTO);
		citaController.eliminarTodasLasCitasDelPaciente(pacienteDTO);

		citaController.confirmarCita(cupo1DTO, pacienteDTO, 1);
		List<CitaDTO> listaCitasAntiguas1 = citaController.obtenerCitasAntiguasPaciente(pacienteDTO);
		List<CitaDTO> listaCitasFuturas1 = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertEquals(0, listaCitasAntiguas1.size());
		assertTrue(listaCitasFuturas1.size() == 1 && listaCitasFuturas1.get(0).getDosis() == 1);

		citaController.buscarYAsignarCitas(pacienteDTO.getIdUsuario());

		List<CitaDTO> listaCitasFuturas2 = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertTrue(listaCitasFuturas2.size() == 2 && listaCitasFuturas2.get(1).getDosis() == 2);
	}

	@Test
	void asignarCitasPacienteCon0Dosis2CitasFuturas() {
		pacienteDTO.setNumDosisAplicadas(0);
		usuarioController.crearUsuarioPaciente(pacienteDTO);
		citaController.eliminarTodasLasCitasDelPaciente(pacienteDTO);

		citaController.confirmarCita(cupo1DTO, pacienteDTO, 1);
		citaController.confirmarCita(cupo2DTO, pacienteDTO, 2);

		List<CitaDTO> listaCitasFuturas1 = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertEquals(2, listaCitasFuturas1.size());

		try {
			citaController.buscarYAsignarCitas(pacienteDTO.getIdUsuario());
		} catch (ResponseStatusException e) {
			assertNotNull(e); // Esperado.
		}

		List<CitaDTO> listaCitasFuturas2 = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertEquals(2, listaCitasFuturas2.size());
	}

	@Test
	void asignarCitasPacienteCon1Dosis1CitaAntigua0CitasFuturas() {
		pacienteDTO.setNumDosisAplicadas(1);
		usuarioController.crearUsuarioPaciente(pacienteDTO);
		citaController.eliminarTodasLasCitasDelPaciente(pacienteDTO);

		citaController.confirmarCita(cupoAntiguoDTO, pacienteDTO, 1);
		List<CitaDTO> listaCitasAntiguas1 = citaController.obtenerCitasAntiguasPaciente(pacienteDTO);
		List<CitaDTO> listaCitasFuturas1 = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertTrue(listaCitasAntiguas1.size() == 1 && listaCitasAntiguas1.get(0).getDosis() == 1);
		assertEquals(0, listaCitasFuturas1.size());

		List<CitaDTO> listaCitasFuturas2 = citaController.buscarYAsignarCitas(pacienteDTO.getIdUsuario());
		assertTrue(listaCitasFuturas2.size() == 1 && listaCitasFuturas2.get(0).getDosis() == 2);
	}

	@Test
	void asignarCitasPacienteCon1Dosis1CitaAntigua1CitaFutura() {
		pacienteDTO.setNumDosisAplicadas(1);
		usuarioController.crearUsuarioPaciente(pacienteDTO);
		citaController.eliminarTodasLasCitasDelPaciente(pacienteDTO);

		citaController.confirmarCita(cupoAntiguoDTO, pacienteDTO, 1);
		citaController.confirmarCita(cupo1DTO, pacienteDTO, 2);
		List<CitaDTO> listaCitasAntiguas1 = citaController.obtenerCitasAntiguasPaciente(pacienteDTO);
		assertTrue(listaCitasAntiguas1.size() == 1 && listaCitasAntiguas1.get(0).getDosis() == 1);
		List<CitaDTO> listaCitasFuturas1 = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertTrue(listaCitasFuturas1.size() == 1 && listaCitasFuturas1.get(0).getDosis() == 2);

		try {
			citaController.buscarYAsignarCitas(pacienteDTO.getIdUsuario());
		} catch (ResponseStatusException e) {
			assertNotNull(e); // Esperado.
		}

		List<CitaDTO> listaCitasAntiguas2 = citaController.obtenerCitasAntiguasPaciente(pacienteDTO);
		assertTrue(listaCitasAntiguas2.size() == 1 && listaCitasAntiguas2.get(0).getDosis() == 1);
		List<CitaDTO> listaCitasFuturas2 = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertTrue(listaCitasFuturas2.size() == 1 && listaCitasFuturas2.get(0).getDosis() == 2);
	}

	@Test
	void asignarCitasPacienteCon2Dosis() {
		pacienteDTO.setNumDosisAplicadas(2);
		usuarioController.crearUsuarioPaciente(pacienteDTO);
		citaController.eliminarTodasLasCitasDelPaciente(pacienteDTO);

		List<CitaDTO> listaInicial = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertEquals(0, listaInicial.size());

		try {
			citaController.buscarYAsignarCitas(pacienteDTO.getIdUsuario());
		} catch (ResponseStatusException e) {
			assertNotNull(e); // Esperado.
		}

		List<CitaDTO> listaFinal = citaController.obtenerCitasFuturasDelPaciente(pacienteDTO.getIdUsuario());
		assertEquals(0, listaFinal.size());
	}

}