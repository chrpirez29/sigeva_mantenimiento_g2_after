package com.equipo3.SIGEVA.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.equipo3.SIGEVA.exception.NumVacunasInvalido;
import com.equipo3.SIGEVA.model.Administrador;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.Cita;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.model.Cupo;
import com.equipo3.SIGEVA.model.Paciente;
import com.equipo3.SIGEVA.model.Rol;
import com.equipo3.SIGEVA.model.Sanitario;
import com.equipo3.SIGEVA.model.Vacuna;

import com.equipo3.SIGEVA.utils.Encriptador;

@Component
public class WrapperDTOtoModel {
	private static Encriptador encrypter = new Encriptador();

	private static final String UNDEFINED = "undefined";
	
	public Administrador administradorDTOtoAdministrador(AdministradorDTO administradorDTO) {
		Administrador administrador = new Administrador();
		if (!administradorDTO.getIdUsuario().equals(UNDEFINED))
			administrador.setIdUsuario(administradorDTO.getIdUsuario());

		administrador.setRol(administradorDTO.getRol().getId());
		administrador.setCentroSalud(administradorDTO.getCentroSalud().getId());
		administrador.setUsername(administradorDTO.getUsername());
		administrador.setCorreo(administradorDTO.getCorreo());
		administrador.setHashPassword(administradorDTO.getHashPassword());
		administrador.setDni(administradorDTO.getDni());
		administrador.setNombre(administradorDTO.getNombre());
		administrador.setApellidos(administradorDTO.getApellidos());
		administrador.setFechaNacimiento(administradorDTO.getFechaNacimiento());
		administrador.setImagen(administradorDTO.getImagen());
		return administrador;
	}

	public static Sanitario sanitarioDTOtoSanitario(SanitarioDTO sanitarioDTO) {
		Sanitario sanitario = new Sanitario();
		if (!sanitarioDTO.getIdUsuario().equals(UNDEFINED))
			sanitario.setIdUsuario(sanitarioDTO.getIdUsuario());

		sanitario.setRol(sanitarioDTO.getRol().getId());
		sanitario.setCentroSalud(sanitarioDTO.getCentroSalud().getId());
		sanitario.setUsername(sanitarioDTO.getUsername());
		sanitario.setCorreo(sanitarioDTO.getCorreo());
		sanitario.setHashPassword(sanitarioDTO.getHashPassword());
		sanitario.setDni(sanitarioDTO.getDni());
		sanitario.setNombre(sanitarioDTO.getNombre());
		sanitario.setApellidos(sanitarioDTO.getApellidos());
		sanitario.setFechaNacimiento(sanitarioDTO.getFechaNacimiento());
		sanitario.setImagen(sanitarioDTO.getImagen());
		return sanitario;
	}

	public Paciente pacienteDTOtoPaciente(PacienteDTO pacienteDTO) {
		Paciente paciente = new Paciente();
		if (!pacienteDTO.getIdUsuario().equals(UNDEFINED))
			paciente.setIdUsuario(pacienteDTO.getIdUsuario());

		paciente.setRol(pacienteDTO.getRol().getId());
		paciente.setCentroSalud(pacienteDTO.getCentroSalud().getId());
		paciente.setUsername(pacienteDTO.getUsername());
		paciente.setCorreo(pacienteDTO.getCorreo());
		paciente.setHashPassword(pacienteDTO.getHashPassword());
		paciente.setDni(pacienteDTO.getDni());
		paciente.setNombre(pacienteDTO.getNombre());
		paciente.setApellidos(pacienteDTO.getApellidos());
		paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
		paciente.setImagen(pacienteDTO.getImagen());
		paciente.setNumDosisAplicadas(encrypter.encriptar(String.valueOf(pacienteDTO.getNumDosisAplicadas())));
		return paciente;
	}

	public CentroSalud centroSaludDTOtoCentroSalud(CentroSaludDTO centroSaludDTO) throws NumVacunasInvalido {
		CentroSalud centroSalud = new CentroSalud();
		if (!centroSaludDTO.getId().equals(UNDEFINED))
			centroSalud.setId(centroSaludDTO.getId());
		centroSalud.setNombreCentro(centroSaludDTO.getNombreCentro());
		centroSalud.setDireccion(centroSaludDTO.getDireccion());
		try {
			centroSalud.setNumVacunasDisponibles(centroSaludDTO.getNumVacunasDisponibles());
		} catch (NumVacunasInvalido e) {
			throw new NumVacunasInvalido();
		}
		centroSalud.setVacuna(centroSaludDTO.getVacuna().getId());
		return centroSalud;
	}

	public static ConfiguracionCupos configuracionCuposDTOtoConfiguracionCupos(
			ConfiguracionCuposDTO configuracionCuposDTO) {
		ConfiguracionCupos configuracionCupos = new ConfiguracionCupos();
		configuracionCupos.setId(configuracionCuposDTO.getId());
		configuracionCupos.setDuracionMinutos(configuracionCuposDTO.getDuracionMinutos());
		configuracionCupos.setNumeroPacientes(configuracionCuposDTO.getNumeroPacientes());
		configuracionCupos.setDuracionJornadaHoras(configuracionCuposDTO.getDuracionJornadaHoras());
		configuracionCupos.setDuracionJornadaMinutos(configuracionCuposDTO.getDuracionJornadaMinutos());
		configuracionCupos.setFechaInicio(configuracionCuposDTO.getFechaInicio());
		return configuracionCupos;
	}

	public static Rol rolDTOToRol(RolDTO rolDTO) {
		Rol rol = new Rol();
		rol.setId(rolDTO.getId());
		rol.setNombre(rolDTO.getNombre());
		return rol;
	}

	public static Vacuna vacunaDTOToVacuna(VacunaDTO vacunaDTO) {
		Vacuna vacuna = new Vacuna();
		vacuna.setId(vacunaDTO.getId());
		vacuna.setNombre(vacunaDTO.getNombre());
		vacuna.setDiasEntreDosis(vacunaDTO.getDiasEntreDosis());
		vacuna.setNumDosis(vacunaDTO.getNumDosis());
		return vacuna;
	}

	public static Cupo cupoDTOToCupo(CupoDTO cupoDTO) {
		Cupo cupo = new Cupo();
		cupo.setUuidCupo(cupoDTO.getUuidCupo());
		cupo.setUuidCentroSalud(cupoDTO.getCentroSalud().getId());
		cupo.setFechaYHoraInicio(cupoDTO.getFechaYHoraInicio());
		cupo.setTamanoActual(cupoDTO.getTamanoActual());
		return cupo;
	}

	public static List<Cupo> allCupoDTOtoCupo(List<CupoDTO> cuposDTO) {
		List<Cupo> cupos = new ArrayList<>();
		for (int i = 0; i < cuposDTO.size(); i++)
			cupos.add(cupoDTOToCupo(cuposDTO.get(i)));
		return cupos;
	}

	public static Cita citaDTOToCita(CitaDTO citaDTO) {
		Cita cita = new Cita();
		cita.setUuidCita(citaDTO.getUuidCita());
		cita.setUuidCupo(citaDTO.getCupo().getUuidCupo());
		cita.setUuidPaciente(citaDTO.getPaciente().getIdUsuario());
		cita.setDosis(citaDTO.getDosis());
		return cita;
	}
}
