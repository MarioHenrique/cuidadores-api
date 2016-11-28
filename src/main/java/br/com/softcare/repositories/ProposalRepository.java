package br.com.softcare.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.softcare.entities.Proposal;
import br.com.softcare.enums.ProposalStatus;

public interface ProposalRepository extends CrudRepository<Proposal, Long> {

	@Query("select p from Proposal p where (p.responsable.id=:id or p.careGiver.id=:id) and p.status=:status")
	public List<Proposal> findAllById(@Param("id")Long id,@Param("status") ProposalStatus status);

	@Query("select p from Proposal p where p.id=:proposalId and (p.responsable.id=:userId or p.careGiver.id=:userId)")
	public Proposal findOne(@Param("proposalId") Long proposalId,@Param("userId") Long userId);

}
