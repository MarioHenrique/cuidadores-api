package br.com.softcare.interceptors;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.softcare.entities.User;
import br.com.softcare.enums.Security;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.messages.ExceptionMessages;
import br.com.softcare.services.UserService;


@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserService usuarioService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		PathAllowed pathAllowed = new PathAllowed(request.getRequestURI(),request.getMethod());
		
		if(!PathAllowed.isValidPath(pathAllowed, PathAllowed.allowedPaths())){
			Optional<String> token = Optional.ofNullable(request.getHeader(Security.TOKEN.getInfo()));
			Optional<User> usuario = Optional.ofNullable(usuarioService.findUserByToken(token.orElseThrow(()->new UnauthorizedClientException(ExceptionMessages.INVALID_TOKEN))));
			usuario.orElseThrow(()->new UnauthorizedClientException(ExceptionMessages.INVALID_TOKEN));
		}
		
		return true;
	}

}
