package com.equipo3.SIGEVA.dto;
/***
 * Token data object transfer, los data object transfer son los que se
 * mandarán desde el front end al back end y viceversa.
 * Este token contendrá el usuario que ha iniciado sesión para poder
 * identificar su rol.
 * 
 * @author Equipo3
 *
 */
public class TokenDTO {
    private String idUsuario;
    private String rol;


    /***
	 * Constructor para la creación del token con argumentos.
	 * @param idUsuario          Id del usuario al que se le va a registrar el token.
	 * @param rol             	 Rol que tiene el usuario al que estamos haciendo referencia.
	 */
    
    public TokenDTO(String idUsuario, String rol) {
        this.idUsuario = idUsuario;
        this.rol = rol;
    }
    
    /***
	 * Método para la devolución del identificador del usuario.
	 * 
	 * @return IdUsuario identificador del usuario.
	 */
    
    public String getIdUsuario() {
        return idUsuario;
    }

    /***
	 * Método para la devolución del rol del usuario.
	 * 
	 * @return rol; rol del usuario.
	 */
    
    public String getRol() {
        return rol;
    }
    
    /***
   	 * Método para la actualización del rol del usuario.
   	 * 
   	 * @param rol Rol nuevo del usuario.
   	 */
    
    public void setRol(String rol) {
        this.rol = rol;
    }
}
