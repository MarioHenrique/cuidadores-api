package br.com.softcare.exceptions;

public class UnauthorizedClientException extends Exception{
	
	private static final long serialVersionUID = 4519270173786257689L;

	public UnauthorizedClientException(String mensagem) {
		super(mensagem);
	}

}
