package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import com.equipo3.SIGEVA.controller.CentroController;
import com.equipo3.SIGEVA.utils.Utilidades;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.equipo3.SIGEVA.controller.UsuarioController;
import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.RolDTO;
import com.equipo3.SIGEVA.dto.SanitarioDTO;
import com.equipo3.SIGEVA.dto.UsuarioLoginDTO;

@SpringBootTest
class LoginTest {
	public static PacienteDTO pacienteDTO;
	public static AdministradorDTO adminDTO;
	public static SanitarioDTO sanitarioDTO;
	public static CentroSaludDTO csDto;
	
    @Autowired
    private UsuarioController usuarioController;

	@Autowired
	private CentroController centroController;

	@Autowired
	private Utilidades utilidades;
    
    @BeforeAll
	static void creacionCentroSalud() {
		csDto = new CentroSaludDTO();
		csDto.setDireccion("test login centro");
		csDto.setNombreCentro("LoginTest");
		csDto.setNumVacunasDisponibles(40);
	}
    
    @BeforeAll
    static void crearPaciente(){
        pacienteDTO = new PacienteDTO();
        pacienteDTO.setCentroSalud(csDto);
        pacienteDTO.setUsername("pruebaLogin");
        pacienteDTO.setNombre("Rodolfo");
        pacienteDTO.setApellidos("Martínez Pocholo");
        pacienteDTO.setRol(new RolDTO());
        pacienteDTO.setDni("06222222A");
        pacienteDTO.setNumDosisAplicadas(0);
        pacienteDTO.setCorreo("rodolfo@gmail.com");
        pacienteDTO.setFechaNacimiento(new Date());
        pacienteDTO.setHashPassword("pass");
    }
    
    @BeforeAll
    static void crearAdmin(){
        adminDTO = new AdministradorDTO();
        adminDTO.setCentroSalud(csDto);
        adminDTO.setUsername("pruebaLogin2");
        adminDTO.setNombre("Maca");
        adminDTO.setApellidos("Mora Palo");
        adminDTO.setRol(new RolDTO());
        adminDTO.setDni("06111111A");
        adminDTO.setCorreo("maca@gmail.com");
        adminDTO.setFechaNacimiento(new Date());
        adminDTO.setHashPassword("pass");
    }
    
    @BeforeAll
    static void crearSanitario(){
        sanitarioDTO = new SanitarioDTO();
        sanitarioDTO.setCentroSalud(csDto);
        sanitarioDTO.setUsername("pruebaLogin3");
        sanitarioDTO.setNombre("Moncho");
        sanitarioDTO.setApellidos("Paul Mor");
        sanitarioDTO.setRol(new RolDTO());
        sanitarioDTO.setDni("06000000A");
        sanitarioDTO.setCorreo("moncho@gmail.com");
        sanitarioDTO.setFechaNacimiento(new Date());
        sanitarioDTO.setHashPassword("pass");
    }

    @Test
    void loginAdminTest(){
    	try {
			centroController.crearCentroSalud(csDto);
        	adminDTO.setRol(utilidades.getRolByNombre("Administrador"));
        	usuarioController.crearUsuarioAdministrador(adminDTO);
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(adminDTO.getUsername());
        	usuarioLogin.setHashPassword(adminDTO.getHashPassword());
        	assertNotNull(usuarioController.login(usuarioLogin));
			utilidades.eliminarUsuario(adminDTO.getUsername());
			utilidades.eliminarCentro(csDto.getId());
    	}catch(Exception e){
    		assertNotNull(e);
			utilidades.eliminarUsuario(adminDTO.getUsername());
			utilidades.eliminarCentro(csDto.getId());
    	}
    
    	
    	
    }
    @Test
    void loginPacienteTest(){
    	try {
			centroController.crearCentroSalud(csDto);
    		pacienteDTO.setRol(utilidades.getRolByNombre("Paciente"));
        	usuarioController.crearUsuarioPaciente(pacienteDTO);
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(pacienteDTO.getUsername());
        	usuarioLogin.setHashPassword(pacienteDTO.getHashPassword());
        	assertNotNull(usuarioController.login(usuarioLogin));
			utilidades.eliminarUsuario(pacienteDTO.getUsername());
			utilidades.eliminarCentro(csDto.getId());
    	}catch(Exception e) {
    		assertNotNull(e);
			utilidades.eliminarUsuario(pacienteDTO.getUsername());
			utilidades.eliminarCentro(csDto.getId());
    	}
    }
    
    @Test
    void loginSanitarioTest(){
    	try {
			centroController.crearCentroSalud(csDto);
    		sanitarioDTO.setRol(utilidades.getRolByNombre("Sanitario"));
    		sanitarioDTO.setCentroSalud(csDto);
        	usuarioController.crearUsuarioSanitario(sanitarioDTO);
        	UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        	usuarioLogin.setUsername(sanitarioDTO.getUsername());
        	usuarioLogin.setHashPassword(sanitarioDTO.getHashPassword());
        	assertNotNull(usuarioController.login(usuarioLogin));
			utilidades.eliminarUsuario(sanitarioDTO.getUsername());
			utilidades.eliminarCentro(csDto.getId());
    	}catch(Exception e){
    		assertNotNull(e);
			utilidades.eliminarUsuario(sanitarioDTO.getUsername());
			utilidades.eliminarCentro(csDto.getId());
    	}
    }
}