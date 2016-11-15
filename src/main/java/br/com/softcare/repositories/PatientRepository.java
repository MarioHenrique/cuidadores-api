package br.com.softcare.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.softcare.entities.Patient;

public interface PatientRepository extends CrudRepository<Patient,Long>{

	@Query("select p from Patient p where p.id=:id and p.userCreator.id=:userId")
	public Patient findByIdAndUserCreatorId(@Param(value = "id") Long id,@Param(value = "userId") Long userId);

	@Query("select p from Patient p where p.userCreator.id=:id")
	public List<Patient> findByUserId(@Param("id") Long id);

}
