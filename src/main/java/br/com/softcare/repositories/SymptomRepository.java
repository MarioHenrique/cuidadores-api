package br.com.softcare.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softcare.entities.Symptom;

@Repository
public interface SymptomRepository extends CrudRepository<Symptom, Long>{

	@Query("select s from Symptom s where s.proposal.id=:id and (s.proposal.responsable.id=:userId or s.proposal.careGiver.id=:userId)")
	List<Symptom> findAllSymptom(@Param("id") Long id,@Param("userId") Long userId);

	@Query("select s from Symptom s where s.id=:symptomId and s.proposal.id=:id and (s.proposal.responsable.id=:userId or s.proposal.careGiver.id=:userId)")
	Symptom findByProposalId(@Param("id")Long id,@Param("symptomId")Long symptomId,@Param("userId") Long userId);

}
