package br.com.softcare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception{
	
	private static final long serialVersionUID = -8961847823885191980L;

	public ResourceNotFoundException(String mensagem) {
		super(mensagem);
	}

}
