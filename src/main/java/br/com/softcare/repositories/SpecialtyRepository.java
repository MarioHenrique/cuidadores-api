package br.com.softcare.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softcare.entities.Specialty;

@Repository
public interface SpecialtyRepository extends CrudRepository<Specialty,Long> {

	@Query("select s from Specialty s where s.careGiver.id=:id")
	public List<Specialty> findAllSpecialty(@Param("id")Long id);

	@Query("select s from Specialty s where s.id=:id and s.careGiver.id=:userId")
	public Specialty findSpecialty(@Param("userId")Long userId,@Param("id") Long id);

}
