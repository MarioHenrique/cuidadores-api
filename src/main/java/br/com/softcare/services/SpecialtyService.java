package br.com.softcare.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.softcare.dto.SpecialtyToUpdateDTO;
import br.com.softcare.entities.Specialty;
import br.com.softcare.entities.User;
import br.com.softcare.enums.Profile;
import br.com.softcare.exceptions.NotAllowedOperationException;
import br.com.softcare.exceptions.ResourceNotFoundException;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.exceptions.UserNotFoundException;
import br.com.softcare.messages.ExceptionMessages;
import br.com.softcare.repositories.SpecialtyRepository;

@Service
public class SpecialtyService {

	@Autowired 
	private SpecialtyRepository specialtyRepository;

	@Autowired
	private RequestService requestService;
	
	public Specialty createSpecialty(Specialty speciality) throws NotAllowedOperationException, UnauthorizedClientException, UserNotFoundException {
		User userRequest = requestService.getUserRequest();
		if(!userRequest.getProfile().contains(Profile.CAREGIVER)){
			throw new NotAllowedOperationException(ExceptionMessages.USER_NOT_ALLOWED);
		}
		speciality.setCareGiver(userRequest);
		specialtyRepository.save(speciality);
		return speciality;
	}

	public List<Specialty> getAllSpecialty() throws UnauthorizedClientException, UserNotFoundException {
		User userRequest = requestService.getUserRequest();
		return specialtyRepository.findAllSpecialty(userRequest.getId());
	}

	public Specialty find(Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException {
		User userRequest = requestService.getUserRequest();
		return Optional.ofNullable(specialtyRepository.findSpecialty(userRequest.getId(),id)).orElseThrow(()-> new ResourceNotFoundException(ExceptionMessages.SPECIALTY_NOT_FOUND));
	}

	public void remove(Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException {
		Specialty specialty = find(id);
		specialtyRepository.delete(specialty);
	}

	public Specialty update(Long id, SpecialtyToUpdateDTO speciality) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException {
		Specialty specialty = find(id);
		specialty.setDescription(speciality.getDescription());
		specialty.setName(speciality.getName());
		specialtyRepository.save(specialty);
		return specialty;
	}
	
	
	
}
