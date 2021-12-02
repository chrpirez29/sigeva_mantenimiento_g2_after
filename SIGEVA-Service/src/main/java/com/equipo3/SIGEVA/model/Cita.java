package com.equipo3.SIGEVA.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/***
 * Entidad Cita.
 * Clase que representará la cita que tendrá un paciente en un cupo con una dosis.
 * 
 * @author Equipo3
 *
 */

@Document
public class Cita {

	@Id
	private String uuidCita;

	@Field
	private String uuidCupo;

	@Field
	private String uuidPaciente;

	@Field
	private int dosis;

	/***
	 * Constructor para la creación de citas sin pasar valores de la cita.
	 * Se crea un identificador aleatorio.
	 */
	
	public Cita() {
		this.uuidCita = UUID.randomUUID().toString();
	}

	/***
	 * Método para la devolución del identificador de la cita.
	 * 
	 * @return uuidCita; identificador.
	 */
	
	public String getUuidCita() {
		return uuidCita;
	}

	/***
	* Método para la actualización del identificador de la cita.
	* 
	* @param uuidCita; Identificador cita nuevo.
	*/
	
	public void setUuidCita(String uuidCita) {
		this.uuidCita = uuidCita;
	}

	/***
	 * Método para la devolución del id del cupo de la cita.
	 * 
	 * @return uuidCupo; identificador del cupo correspondiente a la cita.
	 */
	
	public String getUuidCupo() {
		return uuidCupo;
	}

	/***
	* Método para la actualización del id del cupo de la cita.
	* 
	* @param uuidCupo; identificador del cupo correspondiente a la cita.
	*/
	
	public void setUuidCupo(String uuidCupo) {
		this.uuidCupo = uuidCupo;
	}

	/***
	 * Método para la devolución del identificador paciente de la cita.
	 * 
	 * @return uuidPaciente; identificador del paciente correspondiente a la cita.
	 */
	
	public String getUuidPaciente() {
		return uuidPaciente;
	}

	/***
	* Método para la actualización del identificador del paciente de la cita.
	* 
	* @param uuidPaciente; identificador del paciente de la cita nuevo.
	*/
	
	public void setUuidPaciente(String uuidPaciente) {
		this.uuidPaciente = uuidPaciente;
	}

	/***
	 * Método para la devolución de la dosis en la cita.
	 * 
	 * @return dosis; dosis correspondiente a la cita.
	 */
	
	public int getDosis() {
		return dosis;
	}

	/***
	* Método para la actualización de la dosis en la cita.
	* 
	* @param dosis; dosis de la cita nueva.
	*/
	
	public void setDosis(int dosis) {
		this.dosis = dosis;
	}
}
