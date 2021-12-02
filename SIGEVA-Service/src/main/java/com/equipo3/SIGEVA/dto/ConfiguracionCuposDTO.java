package com.equipo3.SIGEVA.dto;

import java.util.UUID;

/***
 * ConfiguraciónCupos data object transfer, los data object transfer son los que se
 * mandarán desde el front end al back end y viceversa.
 * Clase que representará la configuración que tendrán los cupos donde irán las citas.
 * 
 * @author Equipo3
 *
 */

public class ConfiguracionCuposDTO {

    private String id;
    private int duracionMinutos;
    private int numeroPacientes;
    private int duracionJornadaHoras;
    private int duracionJornadaMinutos;
    private String fechaInicio;

    /***
	 * Constructor para la creación de la configuración de cupos sin pasar valores de esta configuración.
	 * Se crea un identificador aleatorio.
	 */
    
    public ConfiguracionCuposDTO() {
        this.id = UUID.randomUUID().toString();
    }

    /***
	 * Constructor para la creación de configuraciones de cupos pasando los diferentes valores del
	 * objeto.
	 * 
	 * @param duracionMinutos          		Duración en minutos del cupo.
	 * @param numeroPacientes               Número de pacientes que pasarán por ese cupo.
	 * @param duracionJornadaHoras 			La duración de la jornada/día que tendrá el cupo en horas.
	 * @param duracionJornadaMinutos 		La duración de la jornada/día que tendrá el cupo en minutos.
	 * @param fechaInicio 					Fecha en la que empezará el cupo.
	 * 
	 */
    
    public ConfiguracionCuposDTO(int duracionMinutos, int numeroPacientes, int duracionJornadaHoras,
                                 int duracionJornadaMinutos, String fechaInicio) {
        this.id = UUID.randomUUID().toString();
        this.duracionMinutos = duracionMinutos;
        this.numeroPacientes = numeroPacientes;
        this.duracionJornadaHoras = duracionJornadaHoras;
        this.duracionJornadaMinutos = duracionJornadaMinutos;
        this.fechaInicio = fechaInicio;
    }

    /***
	 * Método para la devolución del identificador del cupo.
	 * 
	 * @return id; identificador.
	 */
    
    public String getId() {
        return id;
    }

    /***
	* Método para la actualización del identificador del cupo.
	* 
	* @param id; Identificador nuevo.
	*/
    
    public void setId(String id) {
        this.id = id;
    }

    /***
	 * Método para la actualización de la duración en minutos del cupo.
	 * 
	 * @param duracionMinutos; duración del cupo en minutos.
	 */
    
    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    /***
	* Método para la actualización del número de pacientes que pasan por el cupo.
	* 
	* @param numeroPacientes; Número de pacientes que van a pasar por ese cupo.
	*/
    
    public void setNumeroPacientes(int numeroPacientes) {
        this.numeroPacientes = numeroPacientes;
    }

    /***
	 * Método para la devolución de la duración en minutos del cupo.
	 * 
	 * @return duracionMinutos; duración del cupo en minutos.
	 */
    
    public int getDuracionMinutos() {
        return this.duracionMinutos;
    }

    /***
	* Método para la devolución del número de pacientes que pasan por el cupo.
	* 
	* @return numeroPacientes; Número de pacientes que van a pasar por ese cupo.
	*/
    
    public int getNumeroPacientes() {
        return numeroPacientes;
    }

    /***
	* Método para la devolución de la duración en horas de la jornada del cupo.
	* 
	* @return duracionJornadaHoras; Duración en horas de la jornada del cupo.
	*/
    
    public int getDuracionJornadaHoras() {
        return duracionJornadaHoras;
    }

    /***
	* Método para la actualización de la duración en horas de la jornada del cupo.
	* 
	* @param duracionJornadaHoras; Duración en horas de la jornada del cupo.
	*/
    
    public void setDuracionJornadaHoras(int duracionJornadaHoras) {
        this.duracionJornadaHoras = duracionJornadaHoras;
    }

    /***
	* Método para la devolución de la duración en minutos de la jornada del cupo.
	* 
	* @return duracionJornadaMinutos; Duración en minutos de la jornada del cupo.
	*/
    
    public int getDuracionJornadaMinutos() {
        return duracionJornadaMinutos;
    }

    /***
	* Método para la actualización de la duración en minutos de la jornada del cupo.
	* 
	* @param duracionJornadaMinutos; Duración en minutos de la jornada del cupo.
	*/
    
    public void setDuracionJornadaMinutos(int duracionJornadaMinutos) {
        this.duracionJornadaMinutos = duracionJornadaMinutos;
    }

    /***
	* Método para la devolución de la fecha de inicio en la que se activará el cupo.
	* 
	* @return fechaInicio; devolución de la fecha de inicio en la que se activará el cupo.
	*/
    
    public String getFechaInicio() {
        return fechaInicio;
    }

    /***
	* Método para la actualización de la fecha de inicio en la que se activará el cupo.
	* 
	* @param fechaInicio; devolución de la fecha de inicio en la que se activará el cupo.
	*/
    
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /***
	 * Método que nos da todo la información de la configuración del cupo.
	 */
    
    @Override
    public String toString() {
        return "ConfiguracionCuposDTO{" +
                "duracionMinutos=" + duracionMinutos +
                ", numeroPacientes=" + numeroPacientes +
                ", duracionJornadaHoras=" + duracionJornadaHoras +
                ", duracionJornadaMinutos=" + duracionJornadaMinutos +
                ", fechaInicio='" + fechaInicio + '\'' +
                '}';
    }
}
