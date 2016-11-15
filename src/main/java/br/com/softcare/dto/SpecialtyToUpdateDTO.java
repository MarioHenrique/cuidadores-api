package br.com.softcare.dto;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.softcare.messages.BeanValidatorMessages;

public class SpecialtyToUpdateDTO {

	@NotEmpty(message=BeanValidatorMessages.EMPTY_NAME)
	private String name;
	
	@NotEmpty(message=BeanValidatorMessages.EMPTY_DESCRIPTION)
	private String description;

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
	
	
}
