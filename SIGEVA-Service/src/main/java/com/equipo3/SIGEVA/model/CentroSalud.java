package com.equipo3.SIGEVA.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.equipo3.SIGEVA.exception.NumVacunasInvalido;

/***
 * Entidad centro de salud.
 * 
 * @author Equipo3
 *
 */
@Document
public class CentroSalud {
	@Field
	@Id
	private String id;
	@Field
	private String nombreCentro;
	@Field
	private int numVacunasDisponibles;
	@Field
	private String direccion;
	@Field
	private String vacuna;

	/***
	 * Constructor del objeto
	 */
	public CentroSalud() {
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
	 * Método para la devolución del nombre de centro de salud.
	 * 
	 * @return nombreCentro Devolución del nombre de centro de salud.
	 */
	public String getNombreCentro() {
		return nombreCentro;
	}

	/***
	 * Método para la actualización del nombre de centro.
	 * 
	 * @param nombreCentro Nombre nuevo del centro de salud.
	 */
	public void setNombreCentro(String nombreCentro) {
		this.nombreCentro = nombreCentro;
	}

	/***
	 * Método para la devolución de las dosis disponibles.
	 * 
	 * @return numVacunasDisponibles Dosis disponibles que tiene el centro de salud.
	 */
	public int getNumVacunasDisponibles() {
		return numVacunasDisponibles;
	}

	/***
	 * Método para la actualización de dosis disponibles.
	 * 
	 * @param numVacunasDisponibles Nuevas dosis de vacunas que va a tener
	 *                              disponibles el centro de salud.
	 */
	public void setNumVacunasDisponibles(int numVacunasDisponibles) throws NumVacunasInvalido {
		if (numVacunasDisponibles >= 0) {
			this.numVacunasDisponibles = numVacunasDisponibles;
		} else {
			throw new NumVacunasInvalido("La cantidad de stock especificada es inválida.");
		}
	}

	/***
	 * Método para la devolución de las dirección del centro de salud.
	 * 
	 * @return direccion Devuelve la dirección del centro de salud.
	 */
	public String getDireccion() {
		return direccion;
	}

	/***
	 * Método para la actualización de la dirección del centro de salud
	 * 
	 * @param direccion Nueva dirección que va a tener el centro de salud.
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/***
	 * Metodo para la devolución del objeto vacuna
	 * 
	 * @return Vacuna que tiene asignada el centro de salud.
	 */
	public String getVacuna() {
		return vacuna;
	}

	/***
	 * Método para la actualización del objeto vacuna en el centro de salud.
	 * 
	 * @param vacuna Vacuna nueva que va tener el centro de salud.
	 */
	public void setVacuna(String vacuna) {
		this.vacuna = vacuna;
	}
}
