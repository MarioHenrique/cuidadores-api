package br.com.softcare.dto;

import static br.com.softcare.messages.BeanValidatorMessages.EMPTY_CONTACT;
import static br.com.softcare.messages.BeanValidatorMessages.EMPTY_NAME;
import static br.com.softcare.messages.BeanValidatorMessages.EMPTY_PASSWORD;
import static br.com.softcare.messages.BeanValidatorMessages.EMPTY_ZIPCODE;
import static br.com.softcare.messages.BeanValidatorMessages.LENGTH_PASSWORD;
import static br.com.softcare.messages.BeanValidatorMessages.SIZE_PROFILE;

import java.util.Set;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.softcare.enums.Availability;
import br.com.softcare.enums.Period;
import br.com.softcare.enums.Profile;
import io.swagger.annotations.ApiModelProperty;

public class UserUpdateDTO {

	@NotEmpty(message=EMPTY_NAME)
	private String name;
	
	private String password;
	
	@NotEmpty(message=EMPTY_CONTACT)
	private String contact;
	
	@Size(min=1,message=SIZE_PROFILE)
	@ApiModelProperty(name="profile",notes="AllowedValues=RESPONSABLE, CAREGIVER")
	private Set<Profile> profile;
	
	@ApiModelProperty(name="availability",notes="AllowedValues=SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY")
	private Set<Availability> availability;
	
	@NotEmpty(message=EMPTY_ZIPCODE)
	private String zipCode;
	
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
	@ApiModelProperty(name="Period",notes="AllowedValues=MORNING,AFTERNOON,NIGHT")
	private Set<Period> period;

	
	public Set<Profile> getProfile() {
		return profile;
	}

	public void setProfile(Set<Profile> profile) {
		this.profile = profile;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
}
