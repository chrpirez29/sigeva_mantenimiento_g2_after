package com.equipo3.SIGEVA.dto;

/***
 * UsuarioLogin data object transfer, los data object transfer son los que se
 * mandarán desde el front end al back end y viceversa. Este usuario contendrá
 * el nombre de usuario y el hash de la contraseña para poder iniciar sesión en
 * otras ocasiones.
 * 
 * @author Equipo3
 *
 */
public class UsuarioLoginDTO {
	private String username;
	private String hasPassword;

	/***
	 * Constructor para la creación del token sin argumentos.
	 */

	public UsuarioLoginDTO() {
		username = "";
		hasPassword = "";
	}

	/***
	 * Método para la devolución del nombre de usuario.
	 * 
	 * @return username; nombre de usuario.
	 */

	public String getUsername() {
		return username;
	}

	/***
	 * Método para la actualización del nombre de usuario.
	 * 
	 * @param username; nombre de usuario nuevo.
	 */

	public void setUsername(String username) {
		this.username = username;
	}

	/***
	 * Método para la devolución del hash de la contraseña del usuario.
	 * 
	 * @return hasPassword; hash de la contraseña del usuario.
	 */

	public String getHashPassword() {
		return hasPassword;
	}

	/***
	 * Método para la actualización del hash de la contraseña del usuario.
	 * 
	 * @param hashPassword; hash de la contraseña del usuario nuevo.
	 */

	public void setHashPassword(String hasPassword) {
		this.hasPassword = hasPassword;
	}

}
