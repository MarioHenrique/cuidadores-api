package br.com.softcare.exceptions;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 2947415496866861796L;

	public UserNotFoundException(String mensagem) {
		super(mensagem);
	}
	
}
