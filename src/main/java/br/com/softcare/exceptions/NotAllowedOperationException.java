package br.com.softcare.exceptions;

public class NotAllowedOperationException extends Exception {

	private static final long serialVersionUID = -5225990466826422748L;

	public NotAllowedOperationException(String mensagem){
		super(mensagem);
	}
	
}
