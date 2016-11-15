package br.com.softcare.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.softcare.dto.TreatmentUpdateDTO;
import br.com.softcare.entities.Patient;
import br.com.softcare.entities.Treatment;
import br.com.softcare.entities.User;
import br.com.softcare.exceptions.ResourceNotFoundException;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.exceptions.UserNotFoundException;
import br.com.softcare.messages.ExceptionMessages;
import br.com.softcare.repositories.TreatmentRepository;

@Service
public class TreatmentService {

	@Autowired
	private TreatmentRepository treatmentRepository;

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private RequestService requestService;
	
	public Treatment create(Treatment treatment, Long id) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException {
		Patient patient = patientService.findById(id);
		treatment.setPatient(patient);
		treatmentRepository.save(treatment);
		return treatment;
	}

	public List<Treatment> getTreatmentByPatientId(Long id) throws UnauthorizedClientException, UserNotFoundException {
		User userRequest = requestService.getUserRequest();
		return treatmentRepository.findAllByPatientId(id,userRequest.getId());
	}

	public Treatment update(TreatmentUpdateDTO treatmentToUpdate, Long id, Long treatmentId) throws UnauthorizedClientException, UserNotFoundException {
		User userRequest = requestService.getUserRequest();
		Treatment treatment = treatmentRepository.findByPatientId(treatmentId,id,userRequest.getId());
		treatment.setName(treatmentToUpdate.getName());
		treatment.setDescription(treatmentToUpdate.getDescription());
		treatmentRepository.save(treatment);
		return treatment;
	}

	public Treatment findOne(Long id, Long treatmentId) throws UnauthorizedClientException, UserNotFoundException, ResourceNotFoundException {
		User userRequest = requestService.getUserRequest();
		return Optional.ofNullable(treatmentRepository.findByPatientId(treatmentId,id,userRequest.getId())).orElseThrow(()->new ResourceNotFoundException(ExceptionMessages.TREATMENT_NOT_FOUND));
	}

	public void deletePatient(Long id, Long treatmentId) throws UnauthorizedClientException, UserNotFoundException {
		User userRequest = requestService.getUserRequest();
		Treatment treatment = treatmentRepository.findByPatientId(treatmentId,id,userRequest.getId());
		treatmentRepository.delete(treatment);
	}
	
	
	
	
}
