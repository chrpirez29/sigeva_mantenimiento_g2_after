package com.equipo3.SIGEVA.controller;

import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dto.*;
import com.equipo3.SIGEVA.exception.IdentificadorException;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;
import com.equipo3.SIGEVA.model.Administrador;
import com.equipo3.SIGEVA.model.Paciente;
import com.equipo3.SIGEVA.model.Sanitario;
import com.equipo3.SIGEVA.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Controlador perteneciente al sistema web, sobre la gestión de vacunas COVID
 * SIGEVA, en el se especifican funcionalidades las cuales las puede realizar
 * solo el administrador.S
 *
 * @author Equipo3
 */
@CrossOrigin
@RestController
@RequestMapping("user")
public class UsuarioController {
	@Autowired
	private UsuarioDao administradorDao;

	@Autowired
	private RolDao rolDao;

	@Autowired
	private WrapperModelToDTO wrapperModelToDTO;

	@Autowired
	private WrapperDTOtoModel wrapperDTOtoModel;

	@Autowired
	private CitaController citaController;

	private static final String FRASE_USUARIO_NO_EXISTE = "Usuario no existe en el sistema";
	private static final String FRASE_USUARIO_EXISTENTE = "El usuario ya existe en la base de datos";
	private static final String PACIENTE = "Paciente";
	private static final String SANITARIO = "Sanitario";
	private static final String ADMINISTRADOR = "Administrador";

	/**
	 * Recurso web para la creación de un Administrador.
	 *
	 * @param administradorDTO Datos del administrador proporcionados por otro
	 *                         administrador (usuario), a través de la interfaz
	 *                         gráfica del front end.
	 */
	@PostMapping("/crearUsuarioAdministrador")
	public void crearUsuarioAdministrador(@RequestBody AdministradorDTO administradorDTO) {
		try {
			Administrador administrador = this.wrapperDTOtoModel.administradorDTOtoAdministrador(administradorDTO);
			Optional<Usuario> optUsuario = administradorDao.findByUsername(administrador.getUsername());
			if (optUsuario.isPresent()) {
				throw new UsuarioInvalidoException(FRASE_USUARIO_EXISTENTE);
			}

			administradorDao.save(administrador);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Recurso web para la creación de un Paciente.
	 *
	 * @param pacienteDTO Datos del paciente proporcionados por el administrador
	 *                    (usuario), a través de la interfaz gráfica del front end.
	 */
	@PostMapping("/crearUsuarioPaciente")
	public void crearUsuarioPaciente(@RequestBody PacienteDTO pacienteDTO) {
		try {
			Paciente paciente = this.wrapperDTOtoModel.pacienteDTOtoPaciente(pacienteDTO);
			Optional<Usuario> optUsuario = administradorDao.findByUsername(paciente.getUsername());
			if (optUsuario.isPresent()) {
				throw new UsuarioInvalidoException(FRASE_USUARIO_EXISTENTE);
			}
			administradorDao.save(paciente);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Recurso web para la creación de un Sanitario.
	 *
	 * @param sanitarioDTO Datos del sanitario proporcionados por el administrador
	 *                     (usuario), a través de la interfaz gráfica del front end.
	 */
	@PostMapping("/crearUsuarioSanitario")
	public void crearUsuarioSanitario(@RequestBody SanitarioDTO sanitarioDTO) {
		try {
			Sanitario sanitario = WrapperDTOtoModel.sanitarioDTOtoSanitario(sanitarioDTO);
			Optional<Usuario> optUsuario = administradorDao.findByUsername(sanitario.getUsername());
			if (optUsuario.isPresent()) {
				throw new UsuarioInvalidoException(FRASE_USUARIO_EXISTENTE);
			}

			administradorDao.save(sanitario);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Recurso web para la obtención de todos los roles que se encuentran en la
	 * bbdd.
	 *
	 * @return List<RolDTO> Roles que se encuentran en la bbdd.
	 */
	@GetMapping("/getRoles")
	public List<RolDTO> listarRoles() {
		try {
			return wrapperModelToDTO.allRolToRolDTO(rolDao.findAll());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	/**
	 * Recurso para la obtención de todos los usuario por Rol, teniendo la opción de
	 * devolver todos (rol=Todos)
	 *
	 * @param rol Identificador del rol que van a tener los usuarios, los cuales se
	 *            quieren obtener.
	 * @return List<UsuarioDTO> Lista de usuarios que tiene el rol especificado.
	 */
	@GetMapping("/getUsuariosByRol")
	public List<UsuarioDTO> getUsuarioByRol(@RequestParam String rol) {
		try {
			if (rol.equals("Todos")) {
				return wrapperModelToDTO.allUsuarioToUsuarioDTO(administradorDao.findAll());
			} else {
				return wrapperModelToDTO.allUsuarioToUsuarioDTO(administradorDao.findAllByRol(rol));
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Método para la obtención de un paciente a partir de su identificador.
	 *
	 * @param id Identificador del paciente que se quiere obtener de la bbdd.
	 * @return PacienteDTO Paciente obtenido de la bbdd a partir de su
	 *         identificador.
	 */
	@GetMapping("/paciente")
	public PacienteDTO getPaciente(@RequestParam String id) {
		try {
			Optional<Usuario> optPaciente = administradorDao.findById(id);
			if (optPaciente.isPresent()) {
				return wrapperModelToDTO.pacienteToPacienteDTO(optPaciente.get());
			}
			throw new UsuarioInvalidoException("El paciente no existe en la base de datos");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Recurso web para la obtención de un usuario a partir de su identificador.
	 *
	 * @param idUsuario Identificador de usuario que se quiere obtener de la bbdd.s
	 * @return UsuarioDTO Usuario obtenido de la bbdd a partir de su identificador.
	 */
	@GetMapping("/getUsuarioById")
	public UsuarioDTO getUsuarioById(@RequestParam String idUsuario) {
		try {
			Optional<Usuario> optUsuario = administradorDao.findById(idUsuario);
			if (optUsuario.isPresent()) {
				return wrapperModelToDTO.usuarioToUsuarioDTO(optUsuario.get());
			}
			throw new IdentificadorException("Identificador Usuario " + idUsuario + " no encontrado");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/***
	 * Recurso web para la actualización de los datos de los usuarios
	 * 
	 * @param newUsuarioDTO Usuario con los nuevos datos actualizados y mismo
	 *                      identificador.
	 */
	@PostMapping("/updateUsuario")
	public void editarUsuario(@RequestBody UsuarioDTO newUsuarioDTO) {
		try {
			Optional<Usuario> usuarioOpt = administradorDao.findById(newUsuarioDTO.getIdUsuario());

			if (!usuarioOpt.isPresent()) {
				throw new UsuarioInvalidoException(FRASE_USUARIO_NO_EXISTE);
			}
			UsuarioDTO usuariodto = wrapperModelToDTO.usuarioToUsuarioDTO(usuarioOpt.get());

			if (usuariodto.getRol().getNombre().equals(PACIENTE)) {
				PacienteDTO pacienteDTO = getPaciente(usuariodto.getIdUsuario());
				if (pacienteDTO.getNumDosisAplicadas() != 0) {
					throw new UsuarioInvalidoException(
							"No puedes modificar el centro de un usuario que ya ha aplicado una dosis");
				} else {
					citaController.eliminarCitasFuturasDelPaciente(pacienteDTO);
				}
			}

			UsuarioDTO oldUsuario = wrapperModelToDTO.usuarioToUsuarioDTO(usuarioOpt.get());
			oldUsuario.setCentroSalud(newUsuarioDTO.getCentroSalud());
			oldUsuario.setUsername(newUsuarioDTO.getUsername());
			oldUsuario.setCorreo(newUsuarioDTO.getCorreo());
			oldUsuario.setHashPassword(newUsuarioDTO.getHashPassword());
			oldUsuario.setDni(newUsuarioDTO.getDni());
			oldUsuario.setNombre(newUsuarioDTO.getNombre());
			oldUsuario.setApellidos(newUsuarioDTO.getApellidos());
			oldUsuario.setFechaNacimiento(newUsuarioDTO.getFechaNacimiento());
			oldUsuario.setImagen(newUsuarioDTO.getImagen());

			switch (newUsuarioDTO.getRol().getNombre()) {
			case ADMINISTRADOR:
				administradorDao.save(this.wrapperDTOtoModel.administradorDTOtoAdministrador((AdministradorDTO) oldUsuario));
				break;
			case PACIENTE:
				administradorDao.save(this.wrapperDTOtoModel.pacienteDTOtoPaciente((PacienteDTO) oldUsuario));
				break;
			case SANITARIO:
				administradorDao.save(WrapperDTOtoModel.sanitarioDTOtoSanitario((SanitarioDTO) oldUsuario));
				break;
			default:
				throw new UsuarioInvalidoException("Rol no válido");
			}

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	/***
	 * Recurso web para la eliminación de los usuarios.
	 * 
	 * @param idUsuario Identificador del usuario que ser quiere eliminar del
	 *                  sistema.
	 */
	@DeleteMapping("/deleteUsuario/{idUsuario}")
	public void deleteUsuarioById(@PathVariable String idUsuario) {
		try {
			Optional<Usuario> usuarioOpt = administradorDao.findById(idUsuario);
			if (!usuarioOpt.isPresent()) {
				throw new UsuarioInvalidoException(FRASE_USUARIO_NO_EXISTE);
			}
			UsuarioDTO usuariodto = wrapperModelToDTO.usuarioToUsuarioDTO(usuarioOpt.get());
			if (usuariodto.getRol().getNombre().equals(PACIENTE)) {
				PacienteDTO pacienteDTO = getPaciente(usuariodto.getIdUsuario());
				if (pacienteDTO.getNumDosisAplicadas() != 0) {
					throw new UsuarioInvalidoException(
							"No puedes eliminar el usuario porque ya tiene aplicada 1 o más dosis");
				} else {
					citaController.eliminarCitasFuturasDelPaciente(pacienteDTO);
				}
			} else if (usuariodto.getRol().getNombre().equals(ADMINISTRADOR)) {
				throw new UsuarioInvalidoException("No puedes eliminar el usuario porque es administrador.");
			}
			administradorDao.deleteById(idUsuario);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	/***
	 * Recurso web para el acceso al sistema de los diferentes usuarios, a través de
	 * su correo y contraseña.
	 * 
	 * @param usuarioLoginDTO Usuario el cual ha intentado iniciar sesión.
	 * @return TokenDTO Token a través del cual sabemos si un usuario ha iniciado
	 *         sesión y restringimos funcionalidades a partir de él.
	 */
	@PostMapping("/login")
	public TokenDTO login(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
		try {
			Optional<Usuario> usuarioOpt = administradorDao.findByUsername(usuarioLoginDTO.getUsername());
			if (!usuarioOpt.isPresent()) {
				throw new UsuarioInvalidoException(FRASE_USUARIO_NO_EXISTE);
			}
			UsuarioDTO usuariodto = wrapperModelToDTO.usuarioToUsuarioDTO(usuarioOpt.get());
			if (!usuariodto.getHashPassword().equals(usuarioLoginDTO.getHashPassword())) {
				throw new UsuarioInvalidoException("Contraseña incorrecta");
			}

			return new TokenDTO(usuariodto.getIdUsuario(), usuariodto.getRol().getNombre());

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}
}