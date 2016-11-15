package br.com.softcare.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.softcare.entities.ProcedureRecorded;
import br.com.softcare.entities.Proposal;
import br.com.softcare.entities.User;
import br.com.softcare.exceptions.NotAllowedOperationException;
import br.com.softcare.exceptions.ResourceNotFoundException;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.exceptions.UserNotFoundException;
import br.com.softcare.messages.ExceptionMessages;
import br.com.softcare.repositories.ProcedureRepository;

@Service
public class ProcedureService {

	@Autowired
	private ProcedureRepository procedureRepository;

	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ProposalService proposalService;
	
	public ProcedureRecorded create(Long id, ProcedureRecorded procedure) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException {
		User userRequest = requestService.getUserRequest();
		Proposal proposal = proposalService.find(id);
		if(!proposal.getCareGiver().getId().equals(userRequest.getId())){
			throw new NotAllowedOperationException(ExceptionMessages.USER_NOT_ALLOWED);
		}
		procedure.setCareGiver(userRequest);
		procedure.setProposal(proposal);
		procedure.setPatient(procedure.getPatient());
		procedureRepository.save(procedure);
		return procedure;
	}

	public List<ProcedureRecorded> list(Long id) throws ResourceNotFoundException, UnauthorizedClientException, UserNotFoundException, NotAllowedOperationException {
		User userRequest = requestService.getUserRequest();
		Proposal proposal = proposalService.find(id);
		return procedureRepository.findAllByProposalAndUser(proposal.getId(),userRequest.getId());
	}

	public ProcedureRecorded getOne(Long id, Long procedureId) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException {
		User userRequest = requestService.getUserRequest();
		return Optional.ofNullable(procedureRepository.findOne(id,procedureId,userRequest.getId())).orElseThrow(()->new ResourceNotFoundException(ExceptionMessages.PROCEDURE_NOT_FOUND));
	}

	public void delete(Long id, Long procedureId) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException {
		User userRequest = requestService.getUserRequest();
		ProcedureRecorded procedureRecorded = getOne(id, procedureId);
		if(!procedureRecorded.getProposal().getCareGiver().getId().equals(userRequest.getId())){
			throw new NotAllowedOperationException(ExceptionMessages.USER_NOT_ALLOWED);
		}
		procedureRepository.delete(procedureRecorded);
	}

	public ProcedureRecorded update(Long id, Long procedureId, ProcedureRecorded procedure) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException {
		User userRequest = requestService.getUserRequest();
		ProcedureRecorded procedureRecorded = getOne(id, procedureId);
		if(!procedureRecorded.getProposal().getCareGiver().getId().equals(userRequest.getId())){
			throw new NotAllowedOperationException(ExceptionMessages.USER_NOT_ALLOWED);
		}
		procedureRecorded.setName(procedure.getName());
		procedureRecorded.setDescription(procedure.getDescription());
		procedureRecorded.setProcedureLocation(procedure.getProcedureLocation());
		procedureRecorded.setProcedureInitialDate(procedure.getProcedureInitialDate());
		procedureRecorded.setProcedureFinalDate(procedure.getProcedureFinalDate());
		procedureRepository.save(procedureRecorded);
		return procedureRecorded;
	}
	
}
