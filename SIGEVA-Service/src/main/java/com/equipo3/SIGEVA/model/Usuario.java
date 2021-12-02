package com.equipo3.SIGEVA.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/***
 * Entidad Usuario. Clase que representará al usuario de la aplicación que va a
 * usar el sistema.
 * 
 * @author Equipo3
 *
 */
@Document
public class Usuario {
	@Id
	@Field
	private String idUsuario;
	@Field
	private String rol;
	@Field
	private String centroSalud;
	@Field
	private String username;
	@Field
	private String correo;
	@Field
	private String hashPassword;
	@Field
	private String dni;
	@Field
	private String nombre;
	@Field
	private String apellidos;
	@Field
	private Date fechaNacimiento;
	@Field
	private String imagen;

	/***
	 * Constructor para la creación de usuarios sin pasar valores del usuario. Se
	 * crea un identificador aleatorio.
	 */

	public Usuario() {
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

	public String getRol() {
		return rol;
	}

	/***
	 * Método para la actualización del rol del usuario.
	 * 
	 * @param rolDTO Rol data object nuevo del usuario.
	 */

	public void setRol(String rol) {
		this.rol = rol;
	}

	/***
	 * Método para la devolución del centro de salud del usuario.
	 * 
	 * @return centroSaludDTO; centro de Salud data object del usuario.
	 */

	public String getCentroSalud() {
		return centroSalud;
	}

	/***
	 * Método para la actualización del centro de salud del usuario.
	 * 
	 * @param centroSaludDTO centro de salud data object nuevo del usuario.
	 */

	public void setCentroSalud(String centroSalud) {
		this.centroSalud = centroSalud;
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
}
