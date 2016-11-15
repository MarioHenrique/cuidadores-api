package br.com.softcare.dto;

import static br.com.softcare.messages.BeanValidatorMessages.SIZE_AVAILABILITY;
import static br.com.softcare.messages.BeanValidatorMessages.SIZE_PERIOD;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.softcare.converters.StringToDateConverter;
import br.com.softcare.enums.Availability;
import br.com.softcare.enums.Period;
import io.swagger.annotations.ApiModelProperty;

public class ProposalRequestDTO {

	@NotNull
	private Long careGiverId;
	
	@NotNull
	private Long patientId;
	
	@JsonDeserialize(converter=StringToDateConverter.class)
	private Date initialDate;
	
	@JsonDeserialize(converter=StringToDateConverter.class)
	private Date finalDate;

	@ApiModelProperty(name="availability",notes="AllowedValues=SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY")
	@Size(min=1,message=SIZE_AVAILABILITY)
	private Set<Availability> availability;
	
	@ApiModelProperty(name="Period",notes="AllowedValues=MORNING,AFTERNOON,NIGHT")
	@Size(min=1,message=SIZE_PERIOD)
	private Set<Period> period;
	
	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
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


	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getCareGiverId() {
		return careGiverId;
	}

	public void setCareGiverId(Long careGiverId) {
		this.careGiverId = careGiverId;
	}
	
}
