package com.equipo3.SIGEVA.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.equipo3.SIGEVA.model.Cupo;

@Repository
public interface CupoDao extends MongoRepository<Cupo, String> {

	@Query("{ 'uuidCentroSalud' : ?0 , 'fechaYHoraInicio' : { '$gte' : ?1 } , 'tamanoActual' : { '$lt' : ?2 } }")
	public List<Cupo> buscarCuposLibresAPartirDe(String uuidCentroSalud, Date aPartirDeLaFecha, int maximo);

	@Query("{ 'uuidCentroSalud' : ?0 , 'fechaYHoraInicio' : { '$gte' : ?1 , '$lte' : ?2 }, 'tamanoActual' : { '$lt' : ?3 } }")
	public List<Cupo> buscarCuposLibresDelTramo(String uuidCentroSalud, Date fechaInicio, Date fechaFin, int maximo);

	@Query("{ 'uuidCentroSalud' : ?0 , 'fechaYHoraInicio' : { '$gte' : ?1 , '$lt' : ?2 } }")
	public List<Cupo> buscarTodosCuposDelTramo(String uuidCentroSalud, Date fechaInicio, Date fechaFin);

	@Query("{ 'uuidCentroSalud' : ?0, 'tamanoActual' : { '$gt' : 0 } }")
	public List<Cupo> buscarCuposOcupados(String uuidCentroSalud, Date fecha);
	
	@Query("{ 'uuidCentroSalud' : ?0, 'fechaYHoraInicio' : { '$gte' : ?1 }, 'fechaYHoraInicio' : { '$lt' : ?2 }, 'tamanoActual' : { '$lt' : ?3 } }")
	public List<Cupo> buscarCuposLibreFecha(String uuidCentroSalud, Date fechaInicio, Date fechaFin, int tamanoActual);

	public List<Cupo> findAllByUuidCentroSalud(String uuidCentroSalud);
	
	@Query(value="{fechaYHoraInicio' : { $lte: ?1, $gte: ?2}}")
	public List<Cupo> findByFechaYHoraInicioBetween(Date fechaInicio, Date fechaFin);

	
	
	 


}