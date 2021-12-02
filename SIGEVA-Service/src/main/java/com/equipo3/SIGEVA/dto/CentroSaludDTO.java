package com.equipo3.SIGEVA.dto;

import com.equipo3.SIGEVA.exception.CentroSinStock;
import com.equipo3.SIGEVA.exception.NumVacunasInvalido;

import java.util.UUID;

/***
 * Centro salud data object transfer, los data object transfer son los que se
 * mandarán desde el front end al back end y viceversa.
 * 
 * @author Equipo3
 *
 */
public class CentroSaludDTO {
	private String id;
	private String nombreCentro;
	private int numVacunasDisponibles;
	private String direccion;
	private VacunaDTO vacuna;

	/***
	 * Constructor para la creación del objeto sin argumentos.
	 */
	public CentroSaludDTO() {
		this.id = UUID.randomUUID().toString();
	}

	/***
	 * Constructor para la creación de objetos pasando los diferentes valores del
	 * objeto.
	 * 
	 * @param nombreCentro          Nombre que va a tener el centro de salud.
	 * @param direccion             Dirección que va a tener el centro de salud.
	 * @param numVacunasDisponibles Número de vacunas disponibles que tiene el
	 *                              centro de salud.
	 */
	public CentroSaludDTO(String nombreCentro, String direccion, int numVacunasDisponibles) {
		this.id = UUID.randomUUID().toString();
		this.nombreCentro = nombreCentro;
		this.numVacunasDisponibles = numVacunasDisponibles;
		this.direccion = direccion;
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
	public void setNumVacunasDisponibles(int numVacunasDisponibles) {
		this.numVacunasDisponibles = numVacunasDisponibles;
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
	public VacunaDTO getVacuna() {
		return vacuna;
	}

	/***
	 * Método para la actualización del objeto vacuna en el centro de salud.
	 * 
	 * @param vacunaDTO Vacuna nueva que va tener el centro de salud.
	 */
	public void setVacuna(VacunaDTO vacunaDTO) {
		this.vacuna = vacunaDTO;
	}

	/***
	 * Incrementación del número de dosis de vacunas disponibles que tiene el centro
	 * de salud.
	 * 
	 * @param cantidad Nueva catidad de dosis que tiene el centro de salud.
	 * @throws NumVacunasInvalido Si el número de vacunas es invalido saltará
	 *                            excepción.
	 */
	public void incrementarNumVacunasDisponibles(int cantidad) throws NumVacunasInvalido {
		if (cantidad >= 0)
			this.setNumVacunasDisponibles(this.getNumVacunasDisponibles() + cantidad);
		else
			throw new NumVacunasInvalido("La cantidad a incrementar especificada es inválida.");
	}

	/***
	 * Incrementación del número de dosis de vacunas disponibles que tiene el centro
	 * de salud.
	 * 
	 * @throws CentroSinStock Excepción que salta si el centro de salud no tiene
	 *                        dosis disponibles de vacunas.
	 */
	public void decrementarNumVacunasDisponibles() throws CentroSinStock {
		// No se restará stock hasta que no se confirme como vacunado por un sanitario.
		try {
			if (this.getNumVacunasDisponibles() == 0) {
				throw new CentroSinStock(
						"El centro de salud " + this.getNombreCentro() + " no tiene stock de vacunas.");
			}
			this.setNumVacunasDisponibles(numVacunasDisponibles - 1);
		} catch (CentroSinStock e) {
			throw new CentroSinStock("El centro no dispone de stock.");
		}
	}

	/***
	 * Método que nos da todo la información del centro de salud
	 */
	@Override
	public String toString() {
		return "CentroSaludDTO{" + "id='" + id + '\'' + ", nombreCentro='" + nombreCentro + '\''
				+ ", numVacunasDisponibles=" + numVacunasDisponibles + ", direccion='" + direccion + '\'' + ", vacuna="
				+ vacuna.toString() + '}';
	}
}
