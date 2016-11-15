package br.com.softcare.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softcare.entities.Treatment;

@Repository
public interface TreatmentRepository extends CrudRepository<Treatment,Long> {

	
	@Query("select t from Treatment t where t.patient.id=:id and t.patient.userCreator.id=:userId")
	public List<Treatment> findAllByPatientId(@Param("id") Long id,@Param("userId") Long userId);

	@Query("select t from Treatment t where t.id=:id and t.patient.id=:patientId and t.patient.userCreator.id=:userId")
	public Treatment findByPatientId(@Param("id")Long id,@Param("patientId")Long patientId,@Param("userId") Long userId);
	
}
