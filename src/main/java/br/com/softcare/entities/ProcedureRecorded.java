package br.com.softcare.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
import br.com.softcare.messages.BeanValidatorMessages;
import io.swagger.annotations.ApiModelProperty;

@Entity
public class ProcedureRecorded {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty(access=Access.READ_ONLY)
	@ApiModelProperty(hidden=true)
	private Long id;
	
	@NotEmpty(message=BeanValidatorMessages.EMPTY_NAME)
	private String name;

	@OneToOne
	@JsonIgnore
	private Proposal proposal;
	
	@NotEmpty(message=BeanValidatorMessages.EMPTY_NAME)
	private String description;

	@NotEmpty(message=BeanValidatorMessages.EMPTY_LOCATION)
	private String procedureLocation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(converter=DateToStringConverter.class)
	@JsonDeserialize(converter=StringToDateConverter.class)
	@NotNull(message=BeanValidatorMessages.EMPTY_DATE)
	private Date procedureInitialDate;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(converter=DateToStringConverter.class)
	@JsonDeserialize(converter=StringToDateConverter.class)
	@NotNull(message=BeanValidatorMessages.EMPTY_DATE)
	private Date procedureFinalDate;

	@OneToOne
	@JsonIgnore
	private User careGiver;

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

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProcedureLocation() {
		return procedureLocation;
	}

	public void setProcedureLocation(String procedureLocation) {
		this.procedureLocation = procedureLocation;
	}

	public Date getProcedureInitialDate() {
		return procedureInitialDate;
	}

	public void setProcedureInitialDate(Date procedureInitialDate) {
		this.procedureInitialDate = procedureInitialDate;
	}

	public Date getProcedureFinalDate() {
		return procedureFinalDate;
	}

	public void setProcedureFinalDate(Date procedureFinalDate) {
		this.procedureFinalDate = procedureFinalDate;
	}

	public User getCareGiver() {
		return careGiver;
	}

	public void setCareGiver(User careGiver) {
		this.careGiver = careGiver;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}
