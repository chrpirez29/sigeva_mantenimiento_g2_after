package com.equipo3.SIGEVA.utils;

import java.util.List;
import java.util.Optional;

import com.equipo3.SIGEVA.dao.*;
import com.equipo3.SIGEVA.dto.*;
import com.equipo3.SIGEVA.model.Rol;
import com.equipo3.SIGEVA.model.Vacuna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.exception.IdentificadorException;
import com.equipo3.SIGEVA.model.Usuario;

@CrossOrigin
@RestController
public class Utilidades {

	@Autowired
	private RolDao rolDao;
	@Autowired
	private CentroSaludDao centroSaludDao;
	@Autowired
	private VacunaDao vacunaDao;
	@Autowired
	private ConfiguracionCuposDao configuracionCuposDao;

	@Autowired
	private UsuarioDao administradorDao;
	@Autowired
	private WrapperModelToDTO wrapperModelToDTO;

	private static final String NO_ENCONTRADO = "no encontrado.";

	/**
	 * Recurso web para la obtención de un usuario a partir de su identificador.
	 *
	 * @param idUsuario Identificador de usuario que se quiere obtener de la bbdd.s
	 * @return UsuarioDTO Usuario obtenido de la bbdd a partir de su identificador.
	 * @throws IdentificadorException
	 */
	@GetMapping("/getUsuarioById")
	public UsuarioDTO getUsuarioById(@RequestParam String idUsuario) throws IdentificadorException {
		try {
			Optional<Usuario> optUsuario = administradorDao.findById(idUsuario);
			if (optUsuario.isPresent()) {
				return wrapperModelToDTO.usuarioToUsuarioDTO(optUsuario.get());
			}
			return null;
		} catch (Exception e) {
			throw new IdentificadorException("Identificador Usuario " + idUsuario + NO_ENCONTRADO);
		}
	}

	/**
	 * Eliminación de usuario a partir del nombre de usuario.
	 *
	 * @param username Nombre de usuario que tiene dicho usuario.
	 */
	public void eliminarUsuario(String username) {
		try {
			administradorDao.deleteByUsername(username);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Eliminación de Centro de Salud a partir del identificador de él.
	 *
	 * @param idCentro Identificador del centro de salud.
	 */
	public void eliminarCentro(String idCentro) {
		try {
			centroSaludDao.deleteById(idCentro);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Obtención de un rol de la bbdd, a partir del nombre del rol.
	 *
	 * @param nombreRol Nombre del rol que queremos obtener.
	 * @return RolDTO Rol obtenido de la bbdd.
	 */
	public RolDTO getRolByNombre(String nombreRol) {
		try {
			Optional<Rol> optRol = rolDao.findByNombre(nombreRol);
			if (optRol.isPresent()) {
				return wrapperModelToDTO.rolToRolDTO(optRol.get());
			}
			throw new IdentificadorException("Identificador Rol " + nombreRol + NO_ENCONTRADO);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	/**
	 * Obtención de los pacientes que están gestionados a partir de SIGEVA.
	 *
	 * @return List<PacienteDTO> Lista de pacientes que contiene todos los Pacientes
	 *         que se encuentran en el sistema.
	 */
	public List<PacienteDTO> getPacientes() {
		try {
			List<Usuario> optUsuario = administradorDao.findAllByClass("com.equipo3.SIGEVA.model.Paciente");
			if (!optUsuario.isEmpty()) {
				return wrapperModelToDTO.allPacienteToPacienteDTO(optUsuario);
			}
			throw new IdentificadorException("No hay pacientes registrados");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Método para la inserción de una vacuna a la bbdd´.
	 *
	 * @param vacunaDTO Vacuna que se va a crear en la bbdd.
	 */
	public void addVacuna(VacunaDTO vacunaDTO) {
		try {
			Vacuna vacuna = WrapperDTOtoModel.vacunaDTOToVacuna(vacunaDTO);
			vacunaDao.save(vacuna);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Método para la obtención de la vacuna a partir del nombre de ella.
	 *
	 * @param pfizer Nombre que tiene la vacuna.
	 * @return VacunaDTO Vacuna obtenida de la BBDD a partir de su nombre.
	 */
	public VacunaDTO getVacunaByNombre(String pfizer) {
		try {
			Optional<Vacuna> optVacuna = vacunaDao.findByNombre(pfizer);
			if (optVacuna.isPresent()) {
				return wrapperModelToDTO.vacunaToVacunaDTO(optVacuna.get());

			}
			throw new IdentificadorException("Identificador Vacuna " + pfizer + NO_ENCONTRADO);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Método para la obtención de la vacuna a partir del identificador de ella.
	 *
	 * @param id Identificador de la vacuna que queremos encontrar en la BBDD.
	 * @return VacunaDTO Vacuna obtenida de la BBDD a partir de su identificador.
	 */
	public VacunaDTO getVacunaById(String id) {
		try {
			Optional<Vacuna> optVacuna = vacunaDao.findById(id);
			if (optVacuna.isPresent()) {
				return wrapperModelToDTO.vacunaToVacunaDTO(optVacuna.get());
			}
			throw new IdentificadorException("Identificador Vacuna " + id + NO_ENCONTRADO);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Método para la eliminación de una vacuna, la cual esta disponible.
	 *
	 * @param idVacuna Identificador de la vacuna que se quiere eliminar.s
	 */
	public void eliminarVacuna(String idVacuna) {
		try {
			vacunaDao.deleteById(idVacuna);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Método para la creación de un rol.
	 *
	 * @param rolDTO Rol el cual se quiere crear en la bbdd.
	 */
	public void crearRol(RolDTO rolDTO) {
		try {
			rolDao.save(WrapperDTOtoModel.rolDTOToRol(rolDTO));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Metodo para la eliminación de un rol.
	 *
	 * @param idRol Identificador del rol que se quiere eliminar.
	 */
	public void eliminarRol(String idRol) {
		try {
			rolDao.deleteById(idRol);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Método para la eliminación de la configuración de cupos que tiene activada el
	 * sistema
	 */
	public void eliminarConfiguracionCupos() {
		try {
			List<ConfiguracionCuposDTO> configuracionCuposDTOList = wrapperModelToDTO
					.allConfiguracionCuposToConfiguracionCuposDTO(configuracionCuposDao.findAll());
			configuracionCuposDao.delete(
					WrapperDTOtoModel.configuracionCuposDTOtoConfiguracionCupos(configuracionCuposDTOList.get(0)));

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
