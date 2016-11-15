package br.com.softcare.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.softcare.entities.ProcedureRecorded;

@Repository
public interface ProcedureRepository extends CrudRepository<ProcedureRecorded, Long> {

	@Query("select p from ProcedureRecorded p where p.proposal.id=:proposalId and (p.proposal.responsable.id=:userId or p.proposal.careGiver.id=:userId)")
	List<ProcedureRecorded> findAllByProposalAndUser(@Param("proposalId") Long id,@Param("userId")Long userId);

	@Query("select p from ProcedureRecorded p where p.id=:id and p.proposal.id=:proposalId and (p.proposal.responsable.id=:userId or p.proposal.careGiver.id=:userId)")
	ProcedureRecorded findOne(@Param("proposalId") Long id,@Param("id") Long procedureId,@Param("userId") Long userId);

}
