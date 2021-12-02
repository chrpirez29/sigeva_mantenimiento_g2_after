package com.equipo3.SIGEVA.dto;

import java.util.Date;
import java.util.UUID;

import com.equipo3.SIGEVA.exception.CupoException;

/***
 * CupoDTO data object transfer, los data object transfer son los que se
 * mandarán desde el front end al back end y viceversa. Clase que representará
 * el cupo al que estarán asociados otras entidades para vacunar.
 * 
 * @author Equipo3
 *
 */

public class CupoDTO implements Comparable<CupoDTO> {

	private String uuidCupo;
	private CentroSaludDTO centroSalud;
	private Date fechaYHoraInicio;
	private int tamanoActual;

	/***
	 * Constructor para la creación de cupos sin pasar valores del cupo.
	 * Se crea un identificador aleatorio.
	 */
	
	public CupoDTO() {
		this.uuidCupo = UUID.randomUUID().toString();
	}

	/***
	 * Constructor para la creación de objetos cuposDTO pasando los diferentes valores del
	 * objeto.
	 * 
	 * @param centroSalud           Centro de salud DTO al que va a estar asociado un cupo.
	 * @param fechaYHoraInicio      Fecha y hora en la que se va a iniciar el cupo.
	 * @param tamanoActual 			Tamaño que tendrá el cupo para gestionar citas de vacunas.
	 */
	
	public CupoDTO(CentroSaludDTO centroSalud, Date fechaYHoraInicio, int tamanoActual) {
		this.uuidCupo = UUID.randomUUID().toString();
		this.centroSalud = centroSalud;
		this.fechaYHoraInicio = fechaYHoraInicio;
		this.tamanoActual = tamanoActual;
	}

	/***
	 * Constructor para la creación de objetos cuposDTO pasando los diferentes valores del
	 * objeto.
	 * 
	 * @param uuidCupo           	Id del cupo.
	 * @param centroSalud           Centro de salud DTO al que va a estar asociado un cupo.
	 * @param fechaYHoraInicio      Fecha y hora en la que se va a iniciar el cupo.
	 * @param tamanoActual 			Tamaño que tendrá el cupo para gestionar citas de vacunas.
	 */
	
	public CupoDTO(String uuidCupo, CentroSaludDTO centroSalud, Date fechaYHoraInicio, int tamanoActual) {
		this.uuidCupo = uuidCupo;
		this.centroSalud = centroSalud;
		this.fechaYHoraInicio = fechaYHoraInicio;
		this.tamanoActual = tamanoActual;
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
	 * Método para la devolución del centro de salud del cupo.
	 * 
	 * @return centroSalud; centro de salud del cupo.
	 */
	
	public CentroSaludDTO getCentroSalud() {
		return centroSalud;
	}

	/***
	* Método para la actualización del centor de salud del cupo.
	* 
	* @param centroSalud; Identificador del centro de salud del cupo nuevo.
	*/
	
	public void setCentroSalud(CentroSaludDTO centroSalud) {
		this.centroSalud = centroSalud;
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
	public int compareTo(CupoDTO o) {
		return fechaYHoraInicio.compareTo(o.getFechaYHoraInicio());
	}

	/***
   	 * Método para la comprobar si el cupo se puede incrementar.
   	 * 
   	 * @param maximo; maximo con el que comparar si ha llegado para no aumentar.
   	 */
	
	public void incrementarTamanoActual(int maximo) throws CupoException {
		if (tamanoActual == maximo) {
			throw new CupoException("Ya no cabían más citas en este cupo.");
		} else {
			tamanoActual++;
		}
	}

	/***
   	 * Método para la comprobar si el cupo se puede decrementar viendo si está vacío o no.
   	 * 
   	 */
	
	public void decrementarTamanoActual() throws CupoException {
		if (tamanoActual == 0) {
			throw new CupoException("No había contabilizada ninguna cita programada en este cupo.");
		} else {
			tamanoActual--;
		}
	}
}
