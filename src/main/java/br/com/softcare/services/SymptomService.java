package br.com.softcare.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.softcare.entities.Proposal;
import br.com.softcare.entities.Symptom;
import br.com.softcare.entities.User;
import br.com.softcare.enums.Profile;
import br.com.softcare.enums.ProposalStatus;
import br.com.softcare.exceptions.NotAllowedOperationException;
import br.com.softcare.exceptions.ResourceNotFoundException;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.exceptions.UserNotFoundException;
import br.com.softcare.messages.ExceptionMessages;
import br.com.softcare.repositories.SymptomRepository;

@Service
public class SymptomService {

	@Autowired
	private SymptomRepository symptomRepository;

	@Autowired
	private ProposalService proposalService;
	
	@Autowired
	private RequestService requestService;
	
	public Symptom create(Symptom symptom, Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException {
		User userRequest = requestService.getUserRequest();
		Proposal proposal = proposalService.find(id);
		
		if(!userRequest.getProfile().contains(Profile.CAREGIVER)){
			throw new NotAllowedOperationException(ExceptionMessages.USER_NOT_ALLOWED);
		}
		
		if(!proposal.getStatus().equals(ProposalStatus.INITIALIZED)){
			throw new NotAllowedOperationException(ExceptionMessages.SYMPTOM_NOT_ALLOWED_FOR_PROPOSAL);
		}
		
		symptom.setEventDate(new Date());
		symptom.setCareGiver(userRequest);
		symptom.setProposal(proposal);
		symptom.setPacient(proposal.getPatient());
		symptomRepository.save(symptom);
		return symptom;
	}

	public List<Symptom> getAll(Long id) throws UnauthorizedClientException, UserNotFoundException {
		User userRequest = requestService.getUserRequest();	
		return symptomRepository.findAllSymptom(id,userRequest.getId());
	}

	public Symptom getOne(Long id, Long symptomId) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException {
		User userRequest = requestService.getUserRequest();
		return Optional.ofNullable(symptomRepository.findByProposalId(id,symptomId,userRequest.getId())).orElseThrow(()->new ResourceNotFoundException(ExceptionMessages.SYMPTOM_NOT_FOUND));
	}

	public void delete(Long id, Long symptomId) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException {
		User userRequest = requestService.getUserRequest();
		Symptom symptom = getOne(id, symptomId);
		if(!symptom.getCareGiver().getId().equals(userRequest.getId())){
			throw new NotAllowedOperationException(ExceptionMessages.USER_NOT_ALLOWED);
		}
		symptomRepository.delete(symptom);
	}

	public Symptom update(Symptom symptom, Long id, Long symptomId) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException {
		User userRequest = requestService.getUserRequest();
		Symptom symtomFound = getOne(id, symptomId);
		if(!symtomFound.getCareGiver().getId().equals(userRequest.getId())){
			throw new NotAllowedOperationException(ExceptionMessages.USER_NOT_ALLOWED);
		}
		symtomFound.setDescription(symptom.getDescription());
		symptomRepository.save(symtomFound);
		return symtomFound;
	}
	
	
}
