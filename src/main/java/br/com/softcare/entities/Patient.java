package br.com.softcare.entities;

import static br.com.softcare.messages.BeanValidatorMessages.EMPTY_BIRTH_DAY_DATE;
import static br.com.softcare.messages.BeanValidatorMessages.EMPTY_NAME;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.softcare.converters.DateToStringConverter;
import br.com.softcare.converters.StringToDateConverter;
import io.swagger.annotations.ApiModelProperty;

@Entity
public class Patient {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	@JsonProperty(access=Access.READ_ONLY)
	private Long id;
	
	@NotEmpty(message=EMPTY_NAME)
	private String name;
	
	@NotNull(message=EMPTY_BIRTH_DAY_DATE)
	@JsonSerialize(converter=DateToStringConverter.class)
	@JsonDeserialize(converter=StringToDateConverter.class)
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	private String contact;

	private String healthStatus;
	
	@OneToOne
	@JsonIgnore
	private User userCreator;
	
	@ManyToMany
	@JsonIgnore
	private List<User> careGivers;
	
	@ManyToMany
	@JsonIgnore
	private List<User> responsableGivers;
	
	@OneToMany(mappedBy="patient")
	@ApiModelProperty(hidden=true)
	@JsonProperty(access=Access.READ_ONLY)
	private List<Treatment> treatments;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public User getUserCreator() {
		return userCreator;
	}

	public void setUserCreator(User userCreator) {
		this.userCreator = userCreator;
	}

	public List<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public List<User> getCareGivers() {
		return careGivers;
	}

	public void setCareGivers(List<User> careGivers) {
		this.careGivers = careGivers;
	}

	public String getHealthStatus() {
		return healthStatus;
	}

	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}

	public List<User> getResponsableGivers() {
		return responsableGivers;
	}

	public void setResponsableGivers(List<User> responsableGivers) {
		this.responsableGivers = responsableGivers;
	}

}
