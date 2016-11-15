package br.com.softcare.controllers;

import java.io.IOException;
import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.softcare.exceptions.NotAllowedOperationException;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.exceptions.UserNotFoundException;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(NotAllowedOperationException.class)
	public void notAllowedException(HttpServletResponse response,NotAllowedOperationException e) throws IOException{
		response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public void userNotFoundException(HttpServletResponse response,UserNotFoundException e) throws IOException{
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
	
	@ExceptionHandler(UnauthorizedClientException.class)
	public void unauthorizedClientExceptionHandler(HttpServletResponse response,UnauthorizedClientException e) throws IOException{
		response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	public void invalidParameterException(HttpServletResponse response,InvalidParameterException e) throws IOException{
		response.sendError(HttpStatus.BAD_REQUEST.value(),e.getMessage());
	}
	
}
