package com.equipo3.SIGEVA.dto;

import java.util.Date;
import java.util.UUID;

/***
 * Usuario data object transfer, los data object transfer son los que se
 * mandarán desde el front end al back end y viceversa. Clase que representará
 * al usuario de la aplicación que va a usar el sistema.
 * 
 * @author Equipo3
 *
 */
public class UsuarioDTO {
	private String idUsuario;
	private RolDTO rolDTO;
	private CentroSaludDTO centroSaludDTO;
	private String username;
	private String correo;
	private String hashPassword;
	private String dni;
	private String nombre;
	private String apellidos;
	private Date fechaNacimiento;
	private String imagen;

	/***
	 * Constructor para la creación de usuarios sin pasar valores del usuario. Se
	 * crea un identificador aleatorio.
	 */

	public UsuarioDTO() {
		this.idUsuario = UUID.randomUUID().toString();
	}

	/***
	 * Método para la devolución del identificador del usuario.
	 * 
	 * @return idUsuario identificador.
	 */

	public String getIdUsuario() {
		return idUsuario;
	}

	/***
	 * Método para la actualización del identificador del usuario.
	 * 
	 * @param idUsuario Identificador nuevo.
	 */

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	/***
	 * Método para la devolución del rol del usuario.
	 * 
	 * @return rolDTO; rol data object del usuario.
	 */

	public RolDTO getRol() {
		return rolDTO;
	}

	/***
	 * Método para la actualización del rol del usuario.
	 * 
	 * @param rolDTO Rol data object nuevo del usuario.
	 */

	public void setRol(RolDTO rolDTO) {
		this.rolDTO = rolDTO;
	}

	/***
	 * Método para la devolución del centro de salud del usuario.
	 * 
	 * @return centroSaludDTO; centro de Salud data object del usuario.
	 */

	public CentroSaludDTO getCentroSalud() {
		return centroSaludDTO;
	}

	/***
	 * Método para la actualización del centro de salud del usuario.
	 * 
	 * @param centroSaludDTO centro de salud data object nuevo del usuario.
	 */

	public void setCentroSalud(CentroSaludDTO centroSaludDTO) {
		this.centroSaludDTO = centroSaludDTO;
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
	 * Método para la devolución del correo electrónico de usuario.
	 * 
	 * @return correo; correo electrónico de usuario.
	 */

	public String getCorreo() {
		return correo;
	}

	/***
	 * Método para la actualización del correo electrónico de usuario.
	 * 
	 * @param correo; correo electrónico de usuario nuevo.
	 */

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/***
	 * Método para la devolución del hash de la contraseña del usuario.
	 * 
	 * @return hashPassword; hash de la contraseña del usuario.
	 */

	public String getHashPassword() {
		return hashPassword;
	}

	/***
	 * Método para la actualización del hash de la contraseña del usuario.
	 * 
	 * @param hashPassword; hash de la contraseña del usuario nuevo.
	 */

	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}

	/***
	 * Método para la devolución del dni de usuario.
	 * 
	 * @return dni; dni de usuario.
	 */

	public String getDni() {
		return dni;
	}

	/***
	 * Método para la actualización del dni de usuario.
	 * 
	 * @param dni; dni de usuario nuevo.
	 */

	public void setDni(String dni) {
		this.dni = dni;
	}

	/***
	 * Método para la devolución del nombre de usuario.
	 * 
	 * @return nombre; nombre de usuario.
	 */

	public String getNombre() {
		return nombre;
	}

	/***
	 * Método para la actualización del nombre de usuario.
	 * 
	 * @param nombre; nombre de usuario nuevo.
	 */

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/***
	 * Método para la devolución de los apellidos del usuario.
	 * 
	 * @return apellidos; apellidos del usuario.
	 */

	public String getApellidos() {
		return apellidos;
	}

	/***
	 * Método para la actualización los apellidos del usuario.
	 * 
	 * @param apellidos; los apellidos del usuario nuevo.
	 */

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/***
	 * Método para la devolución de la fecha de nacimiento del usuario.
	 * 
	 * @return fechaNacimiento; fecha de nacimiento del usuario.
	 */

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/***
	 * Método para la actualización la fecha de nacimiento del usuario.
	 * 
	 * @param fechaNacimiento; fecha de nacimiento del usuario nuevo.
	 */

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/***
	 * Método para la devolución de la foto del usuario.
	 * 
	 * @return imagen; foto del usuario.
	 */

	public String getImagen() {
		return imagen;
	}

	/***
	 * Método para la actualización la foto del usuario.
	 * 
	 * @param imagen; foto del usuario nuevo.
	 */

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	/***
	 * Método que nos da todo la información del usuario.
	 */

	@Override
	public String toString() {
		return "UsuarioDTO{" + "idUsuario='" + idUsuario + '\'' + ", rol=" + rolDTO.toString() + ", centroSalud="
				+ centroSaludDTO + ", username='" + username + '\'' + ", correo='" + correo + '\'' + ", hashPassword='"
				+ hashPassword + '\'' + ", dni='" + dni + '\'' + ", nombre='" + nombre + '\'' + ", apellidos='"
				+ apellidos + '\'' + ", fechaNacimiento=" + fechaNacimiento + ", imagen='" + imagen + '\'' + '}';
	}
}
