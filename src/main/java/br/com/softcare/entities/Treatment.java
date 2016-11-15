package br.com.softcare.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.softcare.messages.BeanValidatorMessages;
import io.swagger.annotations.ApiModelProperty;

@Entity
public class Treatment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty(access=Access.READ_ONLY)
	@ApiModelProperty(hidden=true)
	private Long id;
	
	@NotEmpty(message=BeanValidatorMessages.EMPTY_NAME)
	private String name;
	
	@NotEmpty(message=BeanValidatorMessages.EMPTY_DESCRIPTION)
	private String description;

	@OneToOne
	@JsonIgnore
	private Patient patient;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}
