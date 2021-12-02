package com.equipo3.SIGEVA.dao;

import com.equipo3.SIGEVA.model.Vacuna;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/***
 * Interfaz que nos permite introducir, modificar, borrar... vacunas en la bbdd.
 * 
 * @author Equipo3
 *
 */
@Repository
public interface VacunaDao extends MongoRepository<Vacuna, String> {

	Optional<Vacuna> findByNombre(String nombre);
}
