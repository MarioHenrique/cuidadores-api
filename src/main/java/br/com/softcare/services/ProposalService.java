package br.com.softcare.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.softcare.dto.ProposalRequestDTO;
import br.com.softcare.dto.ProposalUpdateStatusRequestDTO;
import br.com.softcare.dto.UserInforDto;
import br.com.softcare.entities.Patient;
import br.com.softcare.entities.Proposal;
import br.com.softcare.entities.User;
import br.com.softcare.enums.Profile;
import br.com.softcare.enums.ProposalStatus;
import br.com.softcare.exceptions.NotAllowedOperationException;
import br.com.softcare.exceptions.ResourceNotFoundException;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.exceptions.UserNotFoundException;
import br.com.softcare.messages.ExceptionMessages;
import br.com.softcare.repositories.ProposalRepository;
import br.com.softcare.validator.StatusAccepted;
import br.com.softcare.validator.StatusCancelled;
import br.com.softcare.validator.StatusChange;
import br.com.softcare.validator.StatusDenied;
import br.com.softcare.validator.StatusInitialized;
import br.com.softcare.validator.StatusPending;

@Service
public class ProposalService {

	@Autowired
	private ProposalRepository proposalRepository;

	@Autowired
	private PatientService patientService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private UserService userService;

	public Proposal create(ProposalRequestDTO proposalRequest) throws UnauthorizedClientException,
			UserNotFoundException, NotAllowedOperationException, ResourceNotFoundException {
		User userRequest = requestService.getUserRequest();

		Patient patient = patientService.findById(proposalRequest.getPatientId());

		Optional<User> user = Optional.ofNullable(userService.find(proposalRequest.getCareGiverId()));
		User careGiver = user.orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.USER_NOT_FOUND));

		if (!careGiver.getProfile().contains(Profile.CAREGIVER)) {
			throw new NotAllowedOperationException(ExceptionMessages.OPERATION_NOT_ALLOWED);
		}

		if (userRequest.getId().equals(careGiver.getId())) {
			throw new NotAllowedOperationException(ExceptionMessages.INVALID_CAREGIVER);
		}

		Proposal proposal = new Proposal();
		proposal.setAvailability(proposalRequest.getAvailability());
		proposal.setPeriod(proposalRequest.getPeriod());
		proposal.setCareGiver(careGiver);
		proposal.setResponsable(userRequest);
		proposal.setProposalDate(new Date());
		proposal.setPropostalInitialDate(proposalRequest.getInitialDate());
		proposal.setPropostalFinalDate(proposalRequest.getFinalDate());
		proposal.setPatient(patient);
		proposal.setStatus(ProposalStatus.PENDING);

		proposalRepository.save(proposal);

		return proposal;
	}

	public List<Proposal> findAll() throws UnauthorizedClientException, UserNotFoundException {
		User userRequest = requestService.getUserRequest();
		return proposalRepository.findAllById(userRequest.getId());
	}

	public Proposal find(Long id) throws ResourceNotFoundException, UnauthorizedClientException, UserNotFoundException {
		User userRequest = requestService.getUserRequest();
		Optional<Proposal> proposal = Optional.ofNullable(proposalRepository.findOne(id, userRequest.getId()));
		return proposal.orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.PROPOSAL_NOT_FOUND));
	}

	public UserInforDto findResponsable(Long id) throws UnauthorizedClientException, UserNotFoundException {
		User userRequest = requestService.getUserRequest();
		Proposal proposal = proposalRepository.findOne(id, userRequest.getId());
		User responsable = proposal.getResponsable();
		UserInforDto userInfo = new UserInforDto(responsable);
		return userInfo;
	}

	public UserInforDto findCareGiver(Long id) throws UnauthorizedClientException, UserNotFoundException {
		User userRequest = requestService.getUserRequest();
		Proposal proposal = proposalRepository.findOne(id, userRequest.getId());
		User careGIver = proposal.getCareGiver();
		UserInforDto userInfo = new UserInforDto(careGIver);
		return userInfo;
	}

	public Proposal update(ProposalUpdateStatusRequestDTO proposalUpdate) throws UnauthorizedClientException,
			UserNotFoundException, ResourceNotFoundException, NotAllowedOperationException {
		User userRequest = requestService.getUserRequest();
		List<ProposalStatus> responsableStatusAllowed = Arrays.asList(ProposalStatus.INITIALIZED,
				ProposalStatus.CANCELED);
		List<ProposalStatus> careGiverStatusAllowed = Arrays.asList(ProposalStatus.ACEPTED, ProposalStatus.DENIED,
				ProposalStatus.CANCELED);

		Proposal proposal = Optional
				.ofNullable(proposalRepository.findOne(proposalUpdate.getProposalId(), userRequest.getId()))
				.orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.PROPOSAL_NOT_FOUND));
		if (userRequest.getId().equals(proposal.getResponsable().getId())) {
			if (responsableStatusAllowed.contains(proposalUpdate.getStatus())) {
				validStatus(proposal.getStatus(), proposalUpdate.getStatus());
				proposal.setStatus(proposalUpdate.getStatus());
			} else {
				throw new NotAllowedOperationException(ExceptionMessages.USER_NOT_ALLOWED);
			}
		} else {
			if (careGiverStatusAllowed.contains(proposalUpdate.getStatus())) {
				proposal.setStatus(proposalUpdate.getStatus());
			} else {
				throw new NotAllowedOperationException(ExceptionMessages.USER_NOT_ALLOWED);
			}
		}

		proposalRepository.save(proposal);

		return proposal;
	}

	private void validStatus(ProposalStatus actual, ProposalStatus toUpdate) throws NotAllowedOperationException {
		boolean update = true;
		switch (actual) {
		case PENDING:
			update = executeValidationChange(new StatusPending(), toUpdate);
			break;
		case ACEPTED:
			update = executeValidationChange(new StatusAccepted(), toUpdate);
			break;
		case DENIED:
			update = executeValidationChange(new StatusDenied(), toUpdate);
			break;
		case CANCELED:
			update = executeValidationChange(new StatusCancelled(), toUpdate);
			break;
		case INITIALIZED:
			update = executeValidationChange(new StatusInitialized(), toUpdate);
			break;
		default:
			update = false;
		}

		if (!update) {
			throw new NotAllowedOperationException(ExceptionMessages.INVALID_STATUS_SEQUENCE);
		}
	}
	
	private boolean executeValidationChange(StatusChange status,ProposalStatus toUpdate){
		boolean response = true;
		switch (toUpdate) {
		case ACEPTED:
			response = status.accept();
			break;
		case DENIED:
			response = status.denie();
			break;
		case CANCELED:
			response = status.cancel();
			break;
		case INITIALIZED:
			response = status.initialize();
			break;
		default:
			response = false;
		}
		return response;
	}

}
