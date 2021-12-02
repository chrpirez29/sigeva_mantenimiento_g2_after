package com.equipo3.SIGEVA.dto;

import java.util.UUID;

/***
 * Objeto Rol data transfer object, es el que se usara para la comunicación
 * entre el front end y back end.
 *
 * @author Equipo3
 *
 */
public class RolDTO {

    String id;
    String nombre;

    /***
     * Constructor para el objeto.
     */
    public RolDTO() {
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

    /***
     * Método para la obtención de toda la información del rol.
     */
    @Override
    public String toString() {
        return "RolDTO{" + "id='" + id + '\'' + ", nombre='" + nombre + '\'' + '}';
    }
}
