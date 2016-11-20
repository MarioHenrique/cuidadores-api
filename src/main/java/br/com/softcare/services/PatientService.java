package br.com.softcare.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.softcare.entities.Patient;
import br.com.softcare.entities.User;
import br.com.softcare.enums.Profile;
import br.com.softcare.exceptions.NotAllowedOperationException;
import br.com.softcare.exceptions.ResourceNotFoundException;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.exceptions.UserNotFoundException;
import br.com.softcare.messages.ExceptionMessages;
import br.com.softcare.repositories.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private RequestService requestService;
	
	public Patient create(Patient patient) throws UnauthorizedClientException, UserNotFoundException, NotAllowedOperationException{
		User userRequest = requestService.getUserRequest();
		Set<Profile> profiles = userRequest.getProfile();
		if(!profiles.contains(Profile.RESPONSABLE)){
			throw new NotAllowedOperationException(ExceptionMessages.OPERATION_NOT_ALLOWED);
		}
		patient.setUserCreator(userRequest);
		patientRepository.save(patient);
		return patient;
	}

	public Patient findById(Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException {
		User userRequest = requestService.getUserRequest();
		Optional<Patient> patient = Optional.ofNullable(patientRepository.findByIdAndUserCreatorId(id,userRequest.getId()));
		return patient.orElseThrow(()-> new ResourceNotFoundException(ExceptionMessages.PATIENT_NOT_FOUND));
	}

	public Patient update(Patient patientToUpdate, Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException {
		User userRequest = requestService.getUserRequest();
		Optional<Patient> patient = Optional.ofNullable(patientRepository.findByIdAndUserCreatorId(id,userRequest.getId()));
		Patient patientActual = patient.orElseThrow(()-> new ResourceNotFoundException(ExceptionMessages.PATIENT_NOT_FOUND));
		patientActual.setContact(patientToUpdate.getContact());
		patientActual.setDateOfBirth(patientToUpdate.getDateOfBirth());
		patientActual.setName(patientToUpdate.getName());
		patientRepository.save(patientActual);
		return patientActual;
	}

	public List<Patient> findAll() throws UserNotFoundException, UnauthorizedClientException {
		return requestService.findAllPatients();
	}

	public void deleteById(Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException {
		User userRequest = requestService.getUserRequest();
		Optional<Patient> patient = Optional.ofNullable(patientRepository.findByIdAndUserCreatorId(id,userRequest.getId()));
		Patient patientToDelete = patient.orElseThrow(()-> new ResourceNotFoundException(ExceptionMessages.PATIENT_NOT_FOUND));
		patientRepository.delete(patientToDelete.getId());
	}

}
