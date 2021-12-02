package com.equipo3.SIGEVA.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/***
 * Entidad Cupo. Clase que representará el cupo al que estarán asociados otras
 * entidades para vacunar.
 * 
 * 
 * @author Equipo3
 *
 */

@Document
public class Cupo implements Comparable<Cupo> {

	@Id
	private String uuidCupo;

	@Field
	private String uuidCentroSalud;

	@Field
	private Date fechaYHoraInicio;

	@Field
	private int tamanoActual;

	/***
	 * Constructor para la creación de cupos sin pasar valores del cupo.
	 * Se crea un identificador aleatorio.
	 */
	
	public Cupo() {
		this.uuidCupo = UUID.randomUUID().toString();
	}

	/***
	 * Método para la devolución del identificador del cupo.
	 * 
	 * @return uuidCupo; identificador del cupo.
	 */
	
	public String getUuidCupo() {
		return uuidCupo;
	}

	/***
	* Método para la actualización del identificador del cupo.
	* 
	* @param uuidCupo; Identificador del cupo nuevo.
	*/
	
	public void setUuidCupo(String uuidCupo) {
		this.uuidCupo = uuidCupo;
	}

	/***
	 * Método para la devolución del identificador del centro de salud del cupo.
	 * 
	 * @return uuidCentroSalud; identificador del centro de salud del cupo.
	 */
	
	public String getUuidCentroSalud() {
		return uuidCentroSalud;
	}

	/***
	 * Método para la actualización del identificador del centro de salud del cupo.
	 * 
	 * @param uuidCentroSalud; identificador del centro de salud del cupo.
	 */
	
	public void setUuidCentroSalud(String uuidCentroSalud) {
		this.uuidCentroSalud = uuidCentroSalud;
	}

	/***
	 * Método para la devolución de la fecha y la hora de inicio del cupo.
	 * 
	 * @return fechaYHoraInicio; fecha y la hora de inicio del cupo.
	 */
	
	public Date getFechaYHoraInicio() {
		return fechaYHoraInicio;
	}

	/***
	 * Método para la actualización de la fecha y la hora de inicio del cupo.
	 * 
	 * @param fechaYHoraInicio; fecha y la hora de inicio del cupo.
	 */
	
	public void setFechaYHoraInicio(Date fechaYHoraInicio) {
		this.fechaYHoraInicio = fechaYHoraInicio;
	}

	/***
	 * Método para la devolución del tamaño del cupo.
	 * 
	 * @return tamanoActual; tamaño del cupo.
	 */
	
	public int getTamanoActual() {
		return tamanoActual;
	}

	/***
	 * Método para la actualización del tamaño del cupo.
	 * 
	 * @param tamanoActual; tamaño del cupo.
	 */
	
	public void setTamanoActual(int tamanoActual) {
		this.tamanoActual = tamanoActual;
	}

	/***
   	 * Método para la comparación de cupos.
   	 * 
   	 * @param o; cupo a comparar.
   	 */
	
	@Override
	public int compareTo(Cupo o) {
		return fechaYHoraInicio.compareTo(o.getFechaYHoraInicio());
	}
}
