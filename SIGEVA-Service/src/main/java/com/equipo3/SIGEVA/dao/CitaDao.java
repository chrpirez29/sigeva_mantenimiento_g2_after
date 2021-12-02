package com.equipo3.SIGEVA.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.equipo3.SIGEVA.model.Cita;

@Repository
public interface CitaDao extends MongoRepository<Cita, String> {

	@Query("{ 'uuidCupo' : ?0 }")
	public List<Cita> buscarCitasDelCupo(String uuidCupo);

	@Query("{ 'uuidPaciente' : ?0 }")
	public List<Cita> buscarCitasDelPaciente(String uuidPaciente);

	public void deleteAllByUuidCupo(String uuidCupo);

	public List<Cita> findAllByUuidCupo(String uuidCupo);

}
