package com.equipo3.SIGEVA.controller;

import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;
import com.equipo3.SIGEVA.dto.ConfiguracionCuposDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.exception.ConfiguracionYaExistente;
import com.equipo3.SIGEVA.exception.IdentificadorException;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/***
 * Controlador (RestController) que cuenta con todos lo recursos web
 * relacionados con la configuración de los cupos.
 * 
 * @author Equipo3
 *
 */
@CrossOrigin
@RestController
@RequestMapping("cnfg")
public class ConfiguracionController {

	@Autowired
	private ConfiguracionCuposDao configuracionCuposDao;
	@Autowired
	private WrapperModelToDTO wrapperModelToDTO;

	/**
	 * Recurso web para la creción de la configuración de cupos que va a tener
	 * activa el sistema.
	 *
	 * @param configuracionCuposDTO Configuración de cupos que le llega desde el
	 *                              front end.
	 */
	@PostMapping("/crearConfCupos")
	public void crearConfiguracionCupos(@RequestBody ConfiguracionCuposDTO configuracionCuposDTO) {

		try {
			ConfiguracionCupos configuracionCupos = WrapperDTOtoModel
					.configuracionCuposDTOtoConfiguracionCupos(configuracionCuposDTO);

			List<ConfiguracionCuposDTO> configuracionCuposDTOList = wrapperModelToDTO
					.allConfiguracionCuposToConfiguracionCuposDTO(configuracionCuposDao.findAll());
			if (configuracionCuposDTOList.isEmpty())
				configuracionCuposDao.save(configuracionCupos);
			else
				throw new ConfiguracionYaExistente("Ya existe una configuración de cupos");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, e.getMessage());
		}

	}

	/**
	 * Método que comprueba si hay o no una configuración de cupos.
	 *
	 * @return boolean Si tiene el sistema una configuración activa o no.
	 */
	@GetMapping("/existConfCupos")
	public boolean existConfiguracionCupos() {
		try {
			List<ConfiguracionCupos> configuracionCuposList = configuracionCuposDao.findAll();
			return !configuracionCuposList.isEmpty();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

	/**
	 * Obtención de la configuración de cupos que tiene configurada el sistema.
	 *
	 * @return ConfiguracionCuposDTO Configuración que tiene activada el sistema.
	 */
	@GetMapping("/getConfCupos")
	public ConfiguracionCuposDTO getConfiguracionCupos() {
		try {
			List<ConfiguracionCuposDTO> configuracionCuposDTOList = this.wrapperModelToDTO
					.allConfiguracionCuposToConfiguracionCuposDTO(configuracionCuposDao.findAll());

			if (configuracionCuposDTOList.isEmpty())
				throw new IdentificadorException("No existe una configuración de cupos");

			return configuracionCuposDTOList.get(0);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
