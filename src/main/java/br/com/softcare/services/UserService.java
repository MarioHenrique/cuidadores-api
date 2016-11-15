package br.com.softcare.services;

import java.security.InvalidParameterException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.softcare.dto.AddressDTO;
import br.com.softcare.dto.UserLoginDTO;
import br.com.softcare.dto.UserUpdateDTO;
import br.com.softcare.entities.User;
import br.com.softcare.exceptions.ResourceNotFoundException;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.exceptions.UserNotFoundException;
import br.com.softcare.messages.ExceptionMessages;
import br.com.softcare.repositories.UserRepository;
import br.com.softcare.util.EncoderUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ExternalService externalService;
	
	public User create(User userToCreate) throws UserNotFoundException {
		userToCreate.setPassword(EncoderUtil.hash(userToCreate.getPassword()));
		userToCreate.setToken(EncoderUtil.hash(userToCreate.getEmail().concat(userToCreate.getPassword())));
		Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userToCreate.getEmail()));
		if(user.isPresent()){
			throw new UserNotFoundException(ExceptionMessages.USER_ALREADY_EXISTS);
		}
		obtainAddress(userToCreate);
		userRepository.save(userToCreate);
		return userToCreate;
	}

	private void obtainAddress(User user) {
		Optional<AddressDTO> address = Optional.ofNullable(externalService.getAddress(user.getZipCode()));
		if(address.isPresent()){
			AddressDTO addressDTO = address.get();
			user.setStreet(addressDTO.getStreet());
			user.setCity(addressDTO.getCity());
			user.setState(addressDTO.getState());
			user.setDistrict(addressDTO.getDistrict());
		}
	}

	public User login(UserLoginDTO usuarioLogin) throws ResourceNotFoundException  {
		usuarioLogin.setPassword(EncoderUtil.hash(usuarioLogin.getPassword()));
		Optional<User> usuarioOp = Optional.ofNullable(userRepository.findByEmailAndPassword(usuarioLogin.getEmail(), usuarioLogin.getPassword()));
		return usuarioOp.orElseThrow(()->new ResourceNotFoundException(ExceptionMessages.USER_NOT_FOUND));
	}

	public User findUserByToken(String token){
		return userRepository.findByToken(token);
	}

	public User update(UserUpdateDTO user) throws UnauthorizedClientException, UserNotFoundException {
		User userToUpdate = requestService.getUserRequest();
		validPassword(user.getPassword());
		userToUpdate.setContact(user.getContact());
		userToUpdate.setName(user.getName());
		userToUpdate.setPassword(StringUtils.isNotBlank(user.getPassword())?EncoderUtil.hash(user.getPassword()):userToUpdate.getPassword());
		userToUpdate.setProfile(user.getProfile());
		userToUpdate.setAvailability(user.getAvailability());
		userToUpdate.setPeriod(user.getPeriod());
		userToUpdate.setZipCode(user.getZipCode());
		obtainAddress(userToUpdate);
		userRepository.save(userToUpdate);
		return userToUpdate;
	}

	private void validPassword(String password) {
		if(StringUtils.isNotBlank(password) && password.length() < 8){
			throw new InvalidParameterException(ExceptionMessages.INVALID_USER_PASSWORD);
		}
	}

	public User find(Long id) {	
		return userRepository.findOne(id);
	}
	
}
