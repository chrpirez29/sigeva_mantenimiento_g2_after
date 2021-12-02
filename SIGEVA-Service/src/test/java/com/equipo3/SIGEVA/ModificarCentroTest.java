package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.CentroController;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.utils.Utilidades;

/**
 * Test usando la técnica TDD para la modificación de lo centros de salud
 * teniendo en cuenta todos los casos especificados por el ProductOwner
 * 
 * @author Equipo3
 *
 */

@SpringBootTest
class ModificarCentroTest {

	@Autowired
	private CentroController centroController;

	@Autowired
	private Utilidades utilidades;

	private static CentroSaludDTO csDto;

	@BeforeAll
	static void creacionCentroSalud() {
		csDto = new CentroSaludDTO();
		csDto.setDireccion("test modificación centro");
		csDto.setNombreCentro("CentroTest modificación");
		csDto.setNumVacunasDisponibles(80);
	}

	/**
	 * Test modificación correta de centro de salud, por parte del administrador, caso de éxito.
	 */
	@Test
	void modificarCentroCorrecto() {
		try {
			centroController.crearCentroSalud(csDto);

			csDto.setDireccion("Modificación dirección test centro Administrador");
			csDto.setNumVacunasDisponibles(30);

			centroController.modificarCentroSalud(csDto);

			assertEquals(centroController.getCentroById(csDto.getId()).toString(), csDto.toString());

			utilidades.eliminarCentro(csDto.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
