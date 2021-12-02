package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.CentroController;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.utils.Utilidades;

@SpringBootTest
class CrearCentroSaludTest {

	@Autowired
	private CentroController centroController;

	@Autowired
	private Utilidades utilidades;

	static CentroSaludDTO centroSaludDTO;

	@BeforeAll
	static void CrearCentroSalud() {
		centroSaludDTO = new CentroSaludDTO();
		centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());
		centroSaludDTO.setDireccion("Calle falsa 123");
		centroSaludDTO.setNumVacunasDisponibles((int) (Math.random() * 1000));
	}

	@Test
	void testCrearCentroSaludBien() {
		centroSaludDTO.setId(UUID.randomUUID().toString());
		centroController.crearCentroSalud(centroSaludDTO);
		assertEquals(centroController.getCentroById(centroSaludDTO.getId()).toString(), centroSaludDTO.toString());
		utilidades.eliminarCentro(centroSaludDTO.getId());
	}

	@Test
	void testCrearCentroSaludExistente() {
		try {
			String uuid = UUID.randomUUID().toString();
			centroSaludDTO.setNombreCentro(uuid);
			centroController.crearCentroSalud(centroSaludDTO);
			centroController.crearCentroSalud(centroSaludDTO);
		} catch (Exception e) {
			utilidades.eliminarCentro(centroSaludDTO.getId());
			Assertions.assertNotNull(e);
		}
	}
}
