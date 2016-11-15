package br.com.softcare.entities;

import static br.com.softcare.messages.BeanValidatorMessages.EMPTY_CONTACT;
import static br.com.softcare.messages.BeanValidatorMessages.EMPTY_EMAIL;
import static br.com.softcare.messages.BeanValidatorMessages.EMPTY_NAME;
import static br.com.softcare.messages.BeanValidatorMessages.EMPTY_PASSWORD;
import static br.com.softcare.messages.BeanValidatorMessages.EMPTY_ZIPCODE;
import static br.com.softcare.messages.BeanValidatorMessages.INVALID_EMAIL;
import static br.com.softcare.messages.BeanValidatorMessages.LENGTH_PASSWORD;
import static br.com.softcare.messages.BeanValidatorMessages.SIZE_PROFILE;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.softcare.enums.Availability;
import br.com.softcare.enums.Period;
import br.com.softcare.enums.Profile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity(name="usr_api")
@ApiModel
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;

	@NotEmpty(message=EMPTY_NAME)
	private String name;

	@NotEmpty(message=EMPTY_EMAIL)
	@Email(message=INVALID_EMAIL)
	@Column(unique = true)
	private String email;

	@NotEmpty(message=EMPTY_CONTACT)
	private String contact;

	@NotEmpty(message=EMPTY_PASSWORD)
	@Length(min=8,message=LENGTH_PASSWORD)
	private String password;

	@ApiModelProperty(hidden = true)
	@JsonProperty(access = Access.READ_ONLY)
	private String token;

	@NotEmpty(message=EMPTY_ZIPCODE)
	private String zipCode;
	
	@JsonProperty(access=Access.READ_ONLY)
	@ApiModelProperty(hidden = true)
	private String district;
	
	@JsonProperty(access=Access.READ_ONLY)
	@ApiModelProperty(hidden = true)
	private String city;
		
	@JsonProperty(access=Access.READ_ONLY)
	@ApiModelProperty(hidden = true)
	private String street;
	
	@JsonProperty(access=Access.READ_ONLY)
	@ApiModelProperty(hidden = true)
	private String state;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@NotNull(message=SIZE_PROFILE)
	@Size(min=1,message=SIZE_PROFILE)
	@ApiModelProperty(name="profile",notes="AllowedValues=RESPONSABLE, CAREGIVER")
	private Set<Profile> profile;

	@ElementCollection(fetch = FetchType.EAGER)
	@ApiModelProperty(name="availability",notes="AllowedValues=SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY")
	private Set<Availability> availability;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@ApiModelProperty(name="Period",notes="AllowedValues=MORNING,AFTERNOON,NIGHT")
	private Set<Period> period;

	@ManyToMany(mappedBy="careGivers")
	@JsonIgnore
	private List<Patient> patient;

	@ManyToMany(mappedBy="responsableGivers")
	@JsonIgnore
	private List<Patient> dependents;

	@OneToMany(mappedBy="careGiver")
	@JsonIgnore
	private List<Proposal> careProposals;

	@OneToMany(mappedBy="responsable")
	@JsonIgnore
	private List<Proposal> responsableProposals;
	
	@OneToMany(mappedBy = "careGiver")
	@JsonIgnore
	private List<Specialty> specialty;

	@OneToMany(mappedBy = "careGiver")
	@JsonIgnore
	private List<ProcedureRecorded> medicalProcedure;

	@OneToMany(mappedBy="careGiver")
	@JsonIgnore
	private List<Symptom> symptomRecorded;
	
	
	public Set<Availability> getAvailability() {
		return availability;
	}

	public void setAvailability(Set<Availability> availability) {
		this.availability = availability;
	}

	public List<Proposal> getCareProposals() {
		return careProposals;
	}

	public void setCareProposals(List<Proposal> careProposals) {
		this.careProposals = careProposals;
	}

	public List<Proposal> getResponsableProposals() {
		return responsableProposals;
	}

	public void setResponsableProposals(List<Proposal> responsableProposals) {
		this.responsableProposals = responsableProposals;
	}

	public List<Symptom> getSymptomRecorded() {
		return symptomRecorded;
	}

	public void setSymptomRecorded(List<Symptom> symptomRecorded) {
		this.symptomRecorded = symptomRecorded;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Set<Profile> getProfile() {
		return profile;
	}

	public void setProfile(Set<Profile> profile) {
		this.profile = profile;
	}

	public List<Patient> getPatient() {
		return patient;
	}

	public void setPatient(List<Patient> patient) {
		this.patient = patient;
	}

	public List<Patient> getDependents() {
		return dependents;
	}

	public void setDependents(List<Patient> dependents) {
		this.dependents = dependents;
	}

	public List<Specialty> getSpecialty() {
		return specialty;
	}

	public void setSpecialty(List<Specialty> specialty) {
		this.specialty = specialty;
	}

	public List<ProcedureRecorded> getMedicalProcedure() {
		return medicalProcedure;
	}

	public void setMedicalProcedure(List<ProcedureRecorded> medicalProcedure) {
		this.medicalProcedure = medicalProcedure;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public Set<Period> getPeriod() {
		return period;
	}

	public void setPeriod(Set<Period> period) {
		this.period = period;
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

}
