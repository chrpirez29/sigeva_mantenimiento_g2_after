package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.UsuarioController;
import com.equipo3.SIGEVA.controller.CentroController;
import com.equipo3.SIGEVA.controller.CitaController;
import com.equipo3.SIGEVA.controller.CupoController;
import com.equipo3.SIGEVA.dao.CitaDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dto.*;
import com.equipo3.SIGEVA.exception.IdentificadorException;
import com.equipo3.SIGEVA.utils.Utilidades;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
class EliminarUsuarioTest {

    public static CentroSaludDTO centroSaludDTO;
    public static CitaDTO citaDTO;
    public static CupoDTO cupoDTO;
    public static PacienteDTO pacienteDTO;
    public static SanitarioDTO sanitarioDTO;
    public static AdministradorDTO administradorDTO;

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private CentroController centroController;

    @Autowired
    private CitaController citaController;

    @Autowired
    private CupoController cupoController;

    @Autowired
    private Utilidades utilidades;

    @Autowired
    CupoDao cupoDao;

    @Autowired
    CitaDao citaDao;

    @BeforeAll
    static void setUpCita() {
        centroSaludDTO = new CentroSaludDTO();
        centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());

        cupoDTO = new CupoDTO();
        cupoDTO.setCentroSalud(centroSaludDTO);

        citaDTO = new CitaDTO();
        citaDTO.setCupo(cupoDTO);
        citaDTO.setPaciente(pacienteDTO);
        cupoDTO.setCentroSalud(centroSaludDTO);
    }

    @BeforeAll
    static void setUpPaciente() {
        pacienteDTO = new PacienteDTO();
        pacienteDTO.setCentroSalud(centroSaludDTO);
        pacienteDTO.setUsername(UUID.randomUUID().toString());
    }

    @BeforeAll
    static void setUpSanitario() {
        sanitarioDTO = new SanitarioDTO();
        sanitarioDTO.setCentroSalud(centroSaludDTO);
        sanitarioDTO.setUsername(UUID.randomUUID().toString());
    }

    @BeforeAll
    static void setUpAdministrador() {
        administradorDTO = new AdministradorDTO();
        administradorDTO.setCentroSalud(centroSaludDTO);
        administradorDTO.setUsername(UUID.randomUUID().toString());
    }

    @Test
    void eliminarUsuarioSanitario() {
        try {
            sanitarioDTO.setRol(utilidades.getRolByNombre("Sanitario"));

            centroController.crearCentroSalud(centroSaludDTO);
            usuarioController.crearUsuarioSanitario(sanitarioDTO);


            usuarioController.deleteUsuarioById(sanitarioDTO.getIdUsuario());

            Assertions.assertThat(utilidades.getUsuarioById(sanitarioDTO.getIdUsuario())).isNull();

            utilidades.eliminarUsuario(sanitarioDTO.getUsername());
            utilidades.eliminarCentro(centroSaludDTO.getId());

        } catch (Exception e) {

        }
    }

    @Test
    void eliminarUsuarioAdministrador() {
        try {
            administradorDTO.setRol(utilidades.getRolByNombre("Administrador"));
            administradorDTO.setCentroSalud(centroSaludDTO);
            centroController.crearCentroSalud(centroSaludDTO);
            usuarioController.crearUsuarioAdministrador(administradorDTO);


            usuarioController.deleteUsuarioById(administradorDTO.getIdUsuario());

        } catch (Exception e) {
            Assertions.assertThat(e).isNotNull();

            utilidades.eliminarUsuario(administradorDTO.getUsername());
            utilidades.eliminarCentro(centroSaludDTO.getId());
        }
    }

    @Test
    void eliminarUsuarioPacienteSinNada() throws IdentificadorException {
        pacienteDTO.setRol(utilidades.getRolByNombre("Paciente"));
        pacienteDTO.setNumDosisAplicadas(0);
        pacienteDTO.setCentroSalud(centroSaludDTO);

        centroController.crearCentroSalud(centroSaludDTO);
        usuarioController.crearUsuarioPaciente(pacienteDTO);


        usuarioController.deleteUsuarioById(pacienteDTO.getIdUsuario());

        Assertions.assertThat(utilidades.getUsuarioById(pacienteDTO.getIdUsuario())).isNull();

        utilidades.eliminarUsuario(pacienteDTO.getUsername());
        utilidades.eliminarCentro(centroSaludDTO.getId());


    }

    @Test
    void eliminarUsuarioPacienteConDosis() {
        try {
            pacienteDTO.setUsername(UUID.randomUUID().toString());
            pacienteDTO.setRol(utilidades.getRolByNombre("Paciente"));
            pacienteDTO.setNumDosisAplicadas(1);
            pacienteDTO.setCentroSalud(centroSaludDTO);


            centroSaludDTO.setNombreCentro(UUID.randomUUID().toString());

            centroController.crearCentroSalud(centroSaludDTO);
            usuarioController.crearUsuarioPaciente(pacienteDTO);

            usuarioController.deleteUsuarioById(pacienteDTO.getIdUsuario());


        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("401 UNAUTHORIZED \"No puedes eliminar el usuario porque ya tiene aplicada 1 o más dosis\"");

            utilidades.eliminarUsuario(pacienteDTO.getUsername());
            utilidades.eliminarCentro(centroSaludDTO.getId());
        }
    }

    @Test
    void eliminarUsuarioPacienteConCitasFuturas() throws IdentificadorException{

        centroSaludDTO.setNumVacunasDisponibles(55);
        pacienteDTO.setRol(utilidades.getRolByNombre("Paciente"));
        pacienteDTO.setNumDosisAplicadas(0);
        pacienteDTO.setCentroSalud(centroSaludDTO);
        cupoDTO.setFechaYHoraInicio(new Date(121, 11, 23));
        centroController.crearCentroSalud(centroSaludDTO);
        usuarioController.crearUsuarioPaciente(pacienteDTO);
        cupoDao.save(WrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
        citaDao.save(WrapperDTOtoModel.citaDTOToCita(citaDTO));
        usuarioController.deleteUsuarioById(pacienteDTO.getIdUsuario());

        Assertions.assertThat(utilidades.getUsuarioById(pacienteDTO.getIdUsuario())).isNull();

        usuarioController.crearUsuarioPaciente(pacienteDTO);
        citaController.eliminarTodasLasCitasDelPaciente(pacienteDTO);
        cupoController.eliminarCupo(cupoDTO.getUuidCupo());
        utilidades.eliminarUsuario(pacienteDTO.getUsername());
        utilidades.eliminarCentro(centroSaludDTO.getId());
    }
}
