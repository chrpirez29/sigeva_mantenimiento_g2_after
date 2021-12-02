package com.equipo3.SIGEVA.dao;

import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import org.springframework.data.mongodb.repository.MongoRepository;

/***
 * Interfaz que nos permite introducir, modificar, borrar... la configuración de
 * los cupos del sistema en la BBDD.
 * 
 * @author Equipo3
 *
 */
public interface ConfiguracionCuposDao extends MongoRepository<ConfiguracionCupos, String> {

}