package com.equipo3.SIGEVA.dto;

import java.util.UUID;

/***
 * Cita data object transfer, los data object transfer son los que se mandarán
 * desde el front end al back end y viceversa. Clase que representará la cita
 * que tendrá un paciente en un cupo con una dosis.
 * 
 * @author Equipo3
 *
 */

public class CitaDTO implements Comparable<CitaDTO> {

	private String uuidCita;
	private CupoDTO cupo;
	private PacienteDTO paciente;
	private int dosis;

	/***
	 * Constructor para la creación de citas sin pasar valores de la cita. Se crea
	 * un identificador aleatorio.
	 */

	public CitaDTO() {
		this.uuidCita = UUID.randomUUID().toString();
	}

	/***
	 * Constructor para la creación de citas pasando los diferentes valores de la
	 * cita.
	 * 
	 * @param cupo     Cupo que va a tener la cita.
	 * @param paciente Paciente que va a tener esta cita.
	 * @param dosis    Número de dosis que le toca al paciente en la cita.
	 */

	public CitaDTO(CupoDTO cupo, PacienteDTO paciente, int dosis) {
		this.uuidCita = UUID.randomUUID().toString();
		this.cupo = cupo;
		this.paciente = paciente;
		this.dosis = dosis;
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
	 * Método para la devolución del cupo de la cita.
	 * 
	 * @return cupo; cupo correspondiente a la cita.
	 */

	public CupoDTO getCupo() {
		return cupo;
	}

	/***
	 * Método para la actualización del cupo de la cita.
	 * 
	 * @param cupo; cupo correspondiente a la cita.
	 */

	public void setCupo(CupoDTO cupo) {
		this.cupo = cupo;
	}

	/***
	 * Método para la devolución del paciente de la cita.
	 * 
	 * @return paciente; paciente correspondiente a la cita.
	 */

	public PacienteDTO getPaciente() {
		return paciente;
	}

	/***
	 * Método para la actualización del paciente de la cita.
	 * 
	 * @param paciente; paciente de la cita nuevo.
	 */

	public void setPaciente(PacienteDTO paciente) {
		this.paciente = paciente;
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

	/***
	 * Método que compara si el cupo es idéntico al pasado por parámetro.
	 * 
	 * @param o; Cita con la que comparar.
	 */

	@Override
	public int compareTo(CitaDTO o) {
		return this.cupo.compareTo(o.getCupo());
	}
}
