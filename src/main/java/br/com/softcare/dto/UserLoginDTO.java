package br.com.softcare.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import static br.com.softcare.messages.BeanValidatorMessages.*;

public class UserLoginDTO {

	@NotEmpty(message=EMPTY_EMAIL)
	@Email(message=INVALID_EMAIL)
	private String email;
	
	@NotEmpty(message=EMPTY_PASSWORD)
	@Length(min=8,message=LENGTH_PASSWORD)
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
