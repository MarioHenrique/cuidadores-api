package br.com.softcare.dto;

import java.util.Set;

import br.com.softcare.entities.User;
import br.com.softcare.enums.Availability;
import br.com.softcare.enums.Period;

public class UserDTO {

	private Long id;
	
	private String state;
	
	private String name;

	private String email;

	private String contact;

	private String zipCode;
	
	private String district;
	
	private String city;
		
	private String street;

	private Set<Availability> availability;
	
	private Set<Period> period;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<Availability> getAvailability() {
		return availability;
	}

	public void setAvailability(Set<Availability> availability) {
		this.availability = availability;
	}

	public Set<Period> getPeriod() {
		return period;
	}

	public void setPeriod(Set<Period> period) {
		this.period = period;
	}

	public static UserDTO convertEntityToDto(User user){
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setAvailability(user.getAvailability());
		userDTO.setCity(user.getCity());
		userDTO.setContact(user.getContact());
		userDTO.setDistrict(user.getDistrict());
		userDTO.setEmail(user.getEmail());
		userDTO.setName(user.getName());
		userDTO.setPeriod(user.getPeriod());
		userDTO.setState(user.getState());
		userDTO.setStreet(user.getStreet());
		userDTO.setZipCode(user.getZipCode());
		return userDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
