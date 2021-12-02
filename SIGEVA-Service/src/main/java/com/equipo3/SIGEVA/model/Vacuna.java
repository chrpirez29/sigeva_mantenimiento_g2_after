package com.equipo3.SIGEVA.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/***
 * Entidad Vacuna.
 * Clase que representará la vacuna que será puesta a los pacientes.
 * 
 * @author Equipo3
 *
 */

@Document
public class Vacuna {

	@Id
	@Field
	private String id;
	@Field
	private String nombre;
	@Field
	private int diasEntreDosis;
	@Field
	private int numDosis;

	/***
	 * Constructor para la creación de vacunas sin pasar valores de la vacuna.
	 * Se crea un identificador aleatorio.
	 */
	
	public Vacuna() {
		this.id = UUID.randomUUID().toString();
	}

	/***
	 * Método para la devolución del identificador de la vacuna.
	 * 
	 * @return id; identificador.
	 */
	
	public String getId() {
		return id;
	}

	/***
	* Método para la actualización del identificador de la vacuna.
	* 
	* @param id; Identificador nuevo.
	*/
	
	public void setId(String id) {
		this.id = id;
	}

	/***
	 * Método para la devolución del nombre de la vacuna.
	 * 
	 * @return nombre; nombre de vacuna.
	 */
	
	public String getNombre() {
		return nombre;
	}

	/***
   	 * Método para la actualización del nombre de la vacuna.
   	 * 
   	 * @param nombre; nombre de vacuna nuevo.
   	 */
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/***
	 * Método para la devolución de los días que van a pasar entre
	 * 1 dosis y otra.
	 * 
	 * @return diasEntreDosis; días entre dosis de la vacuna.
	 */
	
	public int getDiasEntreDosis() {
		return diasEntreDosis;
	}

	/***
   	 * Método para la actualización delos días que van a pasar entre
	 * 1 dosis y otra.
   	 * 
   	 * @param diasEntreDosis; días entre dosis de la vacuna nuevos.
   	 */
	
	public void setDiasEntreDosis(int diasEntreDosis) {
		this.diasEntreDosis = diasEntreDosis;
	}

	/***
	 * Método para la devolución del número de dosis de la vacuna.
	 * 
	 * @return numDosis; cantidad de dosis de la vacuna.
	 */
	
	public int getNumDosis() {
		return numDosis;
	}

	/***
   	 * Método para la actualización del número de dosis de la vacuna.
   	 * 
   	 * @param numDosis; cantidad de dosis de la vacuna nueva.
   	 */
	
	public void setNumDosis(int numDosis) {
		this.numDosis = numDosis;
	}
}
