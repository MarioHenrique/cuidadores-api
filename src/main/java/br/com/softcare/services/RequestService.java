package br.com.softcare.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.softcare.entities.Patient;
import br.com.softcare.entities.User;
import br.com.softcare.enums.Security;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.exceptions.UserNotFoundException;
import br.com.softcare.messages.ExceptionMessages;
import br.com.softcare.repositories.PatientRepository;

@Service
public class RequestService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private PatientRepository patientRepository;
	
	
	public User getUserRequest() throws UnauthorizedClientException, UserNotFoundException {
		Optional<String> token = Optional.ofNullable(request.getHeader(Security.TOKEN.getInfo()));
		Optional<User> user = Optional.ofNullable(userService.findUserByToken(token.orElseThrow(()-> new UnauthorizedClientException(ExceptionMessages.INVALID_TOKEN))));
		return user.orElseThrow(()->new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
	}

	public List<Patient> findAllPatients() throws UserNotFoundException, UnauthorizedClientException {
		Optional<String> token = Optional.ofNullable(request.getHeader(Security.TOKEN.getInfo()));
		Optional<User> user = Optional.ofNullable(userService.findUserByToken(token.orElseThrow(()-> new UnauthorizedClientException(ExceptionMessages.INVALID_TOKEN))));
		User userLogged = user.orElseThrow(()->new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
		List<Patient> patients =patientRepository.findByUserId(userLogged.getId());
		return patients;
	}

	
}
