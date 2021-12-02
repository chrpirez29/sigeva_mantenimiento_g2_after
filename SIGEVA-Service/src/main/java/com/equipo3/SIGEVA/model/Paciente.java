package com.equipo3.SIGEVA.model;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.mongodb.core.mapping.Field;

/***
 * Entidad Paciente la cual hereda de usuario.
 * 
 * @author Equipo3
 *
 */
@Document
public class Paciente extends Usuario {

	@Field
	private String numDosisAplicadas;

	/***
	 * Constructor de la entidada Paciente
	 */
	public Paciente() {
		super();
	}

	/***
	 * Constructor de la entidad paciente.
	 * 
	 * @param numDosisAplicadas Número de dosis aplicadas que tiene el paciente.
	 */
	public Paciente(String numDosisAplicadas) {
		super();
		this.numDosisAplicadas = numDosisAplicadas;
	}

	/***
	 * Método que devuelve el número de dosis aplicadas.
	 * 
	 * @return numDosisAplicadas Número de dosis que tiene el paciente aplicadas.
	 */
	public String getNumDosisAplicadas() {
		return numDosisAplicadas;
	}

	/***
	 * Método para la modificación del número de dosis que tiene le paciente
	 * aplicadas.
	 * 
	 * @param dosis Nuevo número de dosis que tiene aplicadas el paciente.
	 */
	public void setNumDosisAplicadas(String dosis) {
		this.numDosisAplicadas = dosis;
	}

}
