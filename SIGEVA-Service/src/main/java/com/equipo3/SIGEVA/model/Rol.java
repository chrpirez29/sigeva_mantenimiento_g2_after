package com.equipo3.SIGEVA.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/***
 * Entidad Rol
 * 
 * @author Equipo3
 *
 */
@Document
public class Rol {
	@Id
	@Field
	String id;
	@Field
	String nombre;

	/***
	 * Constructor para el objeto.
	 */
	public Rol() {
		this.id = UUID.randomUUID().toString();
	}

	/***
	 * Método para la devolución del identificador.
	 * 
	 * @return Id identificador.
	 */
	public String getId() {
		return id;
	}

	/***
	 * Método para la actualización del identificador.
	 * 
	 * @param id Identificador nuevo.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/***
	 * Método para la devolución del nombre del rol.
	 * 
	 * @return nombre Devolución del nombre del rol.
	 */
	public String getNombre() {
		return nombre;
	}

	/***
	 * Método para la actualización del nombre del rol.
	 * 
	 * @param nombre Nombre nuevo del rol.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
