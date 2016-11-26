package br.com.softcare.entities;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.softcare.converters.DateToStringConverter;
import br.com.softcare.enums.Availability;
import br.com.softcare.enums.Period;
import br.com.softcare.enums.ProposalStatus;
import io.swagger.annotations.ApiModelProperty;

@Entity
public class Proposal {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;

	@OneToOne
	@JsonIgnore
	private User responsable;
	
	@OneToMany(mappedBy="proposal")
	@JsonIgnore
	private List<Symptom> symptomRecorded;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(converter=DateToStringConverter.class)
	private Date propostalInitialDate;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(converter=DateToStringConverter.class)
	private Date propostalFinalDate;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@ApiModelProperty(name="availability",notes="AllowedValues=SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY")
	private Set<Availability> availability;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@ApiModelProperty(name="Period",notes="AllowedValues=MORNING,AFTERNOON,NIGHT")
	private Set<Period> period;
	
	@ManyToOne
	private Patient patient;
	
	@OneToMany(mappedBy="proposal")
	@JsonIgnore
	private List<ProcedureRecorded> procedureRecorded;
	
	@OneToOne
	private User careGiver;
	
	@Temporal(TemporalType.DATE)
	@JsonSerialize(converter=DateToStringConverter.class)
	@ApiModelProperty(hidden = true)
	@JsonProperty(access = Access.READ_ONLY)
	private Date proposalDate;
	
	private ProposalStatus status;
	
	public List<Symptom> getSymptomRecorded() {
		return symptomRecorded;
	}

	public void setSymptomRecorded(List<Symptom> symptomRecorded) {
		this.symptomRecorded = symptomRecorded;
	}

	public Date getPropostalInitialDate() {
		return propostalInitialDate;
	}

	public void setPropostalInitialDate(Date propostalInitialDate) {
		this.propostalInitialDate = propostalInitialDate;
	}

	public Date getPropostalFinalDate() {
		return propostalFinalDate;
	}

	public void setPropostalFinalDate(Date propostalFinalDate) {
		this.propostalFinalDate = propostalFinalDate;
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

	public List<ProcedureRecorded> getProcedureRecorded() {
		return procedureRecorded;
	}

	public void setProcedureRecorded(List<ProcedureRecorded> procedureRecorded) {
		this.procedureRecorded = procedureRecorded;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getResponsable() {
		return responsable;
	}

	public void setResponsable(User responsable) {
		this.responsable = responsable;
	}

	public Date getProposalDate() {
		return proposalDate;
	}

	public void setProposalDate(Date proposalDate) {
		this.proposalDate = proposalDate;
	}

	public ProposalStatus getStatus() {
		return status;
	}

	public void setStatus(ProposalStatus status) {
		this.status = status;
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
