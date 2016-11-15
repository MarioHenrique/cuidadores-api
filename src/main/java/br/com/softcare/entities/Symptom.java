package br.com.softcare.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.softcare.converters.DateToStringConverter;
import br.com.softcare.messages.BeanValidatorMessages;
import io.swagger.annotations.ApiModelProperty;

@Entity
public class Symptom {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty(access=Access.READ_ONLY)
	@ApiModelProperty(hidden=true)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty(access=Access.READ_ONLY)
	@JsonSerialize(converter=DateToStringConverter.class)
	@ApiModelProperty(hidden=true)
	private Date eventDate;
	
	@NotEmpty(message=BeanValidatorMessages.EMPTY_DESCRIPTION)
	private String description;

	@OneToOne
	@JsonIgnore
	private Proposal proposal;
	
	@OneToOne
	@JsonIgnore
	private Patient pacient;
	
	@OneToOne
	@JsonIgnore
	private User careGiver;
	
	
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Patient getPacient() {
		return pacient;
	}

	public void setPacient(Patient pacient) {
		this.pacient = pacient;
	}

	public User getCareGiver() {
		return careGiver;
	}

	public void setCareGiver(User careGiver) {
		this.careGiver = careGiver;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
