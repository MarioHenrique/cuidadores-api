package br.com.softcare.services;

import static br.com.softcare.repositories.UserSpecs.hasAvailability;
import static br.com.softcare.repositories.UserSpecs.hasPeriod;
import static br.com.softcare.repositories.UserSpecs.isCareGiver;
import static br.com.softcare.repositories.UserSpecs.withCity;
import static br.com.softcare.repositories.UserSpecs.withContact;
import static br.com.softcare.repositories.UserSpecs.withDistrict;
import static br.com.softcare.repositories.UserSpecs.withEmail;
import static br.com.softcare.repositories.UserSpecs.withState;
import static br.com.softcare.repositories.UserSpecs.withStreet;
import static br.com.softcare.repositories.UserSpecs.withZipCode;
import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import br.com.softcare.dto.AddressDTO;
import br.com.softcare.dto.PageAbleImpl;
import br.com.softcare.dto.UserDTO;
import br.com.softcare.entities.User;
import br.com.softcare.enums.Availability;
import br.com.softcare.enums.Period;
import br.com.softcare.enums.Profile;
import br.com.softcare.exceptions.ResourceNotFoundException;
import br.com.softcare.messages.ExceptionMessages;
import br.com.softcare.repositories.CareGiverRepository;

@Service
public class CareGiverService {

	@Autowired
	private CareGiverRepository careGiverRepository;

	public UserDTO find(Long id) throws ResourceNotFoundException {
		Optional<User> user = Optional.ofNullable(careGiverRepository.findById(id));
		User userResulted = user
				.orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.CARE_GIVER_NOT_FOUND));
		if (!userResulted.getProfile().contains(Profile.CAREGIVER)) {
			throw new ResourceNotFoundException(ExceptionMessages.CARE_GIVER_NOT_FOUND);
		}
		return UserDTO.convertEntityToDto(userResulted);
	}

	public Page<UserDTO> findAll(AddressDTO address, String email, String contact, Set<Availability> availability,
			Set<Period> period, Integer page, Integer pageSize) {
		Specifications<User> spec = where(isCareGiver());

		if (StringUtils.isNotBlank(address.getCity())) {
			spec = spec.and(withCity(address.getCity()));
		}

		if (StringUtils.isNotBlank(address.getDistrict())) {
			spec = spec.and(withDistrict(address.getDistrict()));
		}

		if (StringUtils.isNotBlank(address.getState())) {
			spec = spec.and(withState(address.getState()));
		}

		if (StringUtils.isNotBlank(address.getStreet())) {
			spec = spec.and(withStreet(address.getStreet()));
		}

		if (StringUtils.isNotBlank(address.getZipcode())) {
			spec = spec.and(withZipCode(address.getZipcode()));
		}

		if (StringUtils.isNotBlank(email)) {
			spec = spec.and(withEmail(email));
		}

		if (StringUtils.isNotBlank(contact)) {
			spec = spec.and(withContact(contact));
		}

		if (availability != null && !availability.isEmpty()) {
			for (Availability a : availability)
				spec = spec.and(hasAvailability(a));
		}

		if (period != null && !period.isEmpty()) {
			for (Period p : period)
				spec = spec.and(hasPeriod(p));
		}

		Pageable pageAble = new PageAbleImpl(page,pageSize);
		Page<User> users = careGiverRepository.findAll(spec,pageAble);
		List<UserDTO> usersDTO = new ArrayList<>();
		for(User user: users){
			usersDTO.add(UserDTO.convertEntityToDto(user));
		}
		Page<UserDTO> usersPageDTO = new PageImpl<UserDTO>(usersDTO,pageAble,careGiverRepository.count(spec));
		
		return usersPageDTO;
	}

}
