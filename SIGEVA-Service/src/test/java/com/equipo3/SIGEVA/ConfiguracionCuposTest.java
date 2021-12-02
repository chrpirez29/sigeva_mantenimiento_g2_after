package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.controller.ConfiguracionController;
import com.equipo3.SIGEVA.dto.ConfiguracionCuposDTO;
import com.equipo3.SIGEVA.utils.Utilidades;

@SpringBootTest
class ConfiguracionCuposTest {

	@Autowired
	private ConfiguracionController configuracionController;

	@Autowired
	private Utilidades utilidades;

	static ConfiguracionCuposDTO configuracionCuposDTO;
	static ConfiguracionCuposDTO existConfiguracionCuposDTO;

	@BeforeAll
	static void start() {
		configuracionCuposDTO = new ConfiguracionCuposDTO(60, 5, 13, 0, "2021-11-08T07:00");
		existConfiguracionCuposDTO = new ConfiguracionCuposDTO(60, 3, 13, 0, "2021-11-08T07:00");
	}

	@Test
	void crearConfiguracionCupos() {
		if (this.configuracionController.existConfiguracionCupos() == false) {
			this.configuracionController.crearConfiguracionCupos(configuracionCuposDTO);
			assertEquals(this.configuracionController.getConfiguracionCupos().toString(),
					configuracionCuposDTO.toString());
			this.utilidades.eliminarConfiguracionCupos();

		} else {
			existConfiguracionCuposDTO = configuracionController.getConfiguracionCupos();
			this.utilidades.eliminarConfiguracionCupos();

			this.configuracionController.crearConfiguracionCupos(configuracionCuposDTO);
			assertEquals(this.configuracionController.getConfiguracionCupos().toString(),
					configuracionCuposDTO.toString());

			this.utilidades.eliminarConfiguracionCupos();
			this.configuracionController.crearConfiguracionCupos(existConfiguracionCuposDTO);
		}
	}

	@Test
	void crearConfiguracionCuposYaExistente() {
		boolean configuracionExistente = true;
		try {
			configuracionCuposDTO.setId(UUID.randomUUID().toString());
			if (this.configuracionController.existConfiguracionCupos() == false) {
				this.configuracionController.crearConfiguracionCupos(configuracionCuposDTO);
				configuracionExistente = false;
				this.configuracionController.crearConfiguracionCupos(configuracionCuposDTO);
			} else {
				this.configuracionController.crearConfiguracionCupos(configuracionCuposDTO);
			}
		} catch (Exception e) {
			if (configuracionExistente == false) {
				this.utilidades.eliminarConfiguracionCupos();
			}
			assertEquals("208 ALREADY_REPORTED \"Ya existe una configuración de cupos\"", e.getMessage());

		}
	}

	@Test
	void getConfiguracionCupos() {
		if (this.configuracionController.existConfiguracionCupos() == false) {
			Assertions.assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(() -> {
				this.configuracionController.getConfiguracionCupos();
			});
		} else {
			ConfiguracionCuposDTO configuracionCuposDTO = this.configuracionController.getConfiguracionCupos();
			assertNotNull(configuracionCuposDTO);
		}
	}
}