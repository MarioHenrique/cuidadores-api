package br.com.softcare.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.softcare.dto.UserLoginDTO;
import br.com.softcare.dto.UserUpdateDTO;
import br.com.softcare.entities.User;
import br.com.softcare.exceptions.ResourceNotFoundException;
import br.com.softcare.exceptions.UnauthorizedClientException;
import br.com.softcare.exceptions.UserNotFoundException;
import br.com.softcare.services.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class UserController extends Controller{

	@Autowired
	private UserService userService;
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Criação de um usuario no sistema",
				  notes="A criação do usuario é para ambos os perfis")
	@RequestMapping(value="/api/user",method=RequestMethod.POST)
	public User createUser(@RequestBody @Valid User user,BindingResult result) throws UserNotFoundException{
		verifyInvalidParam(result);
		return userService.create(user);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Login do usuario",
				  notes="A partir do login o usuario recupera seu token unico para realizar as operações")
	@RequestMapping(value="/api/user/login",method=RequestMethod.POST)
	public User login(@RequestBody @Valid UserLoginDTO user,BindingResult result) throws ResourceNotFoundException{
		verifyInvalidParam(result);
		return userService.login(user);
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Atualização de usuario",
	  notes="As informações são alteradas para o usuario logado, as unicas informações passiveis de alteração é o nome,telefone,senha e seu perfil")
	@RequestMapping(value="/api/user",method=RequestMethod.PUT)
	public User updateUser(@RequestBody @Valid UserUpdateDTO user,BindingResult result) throws UnauthorizedClientException, UserNotFoundException{
		verifyInvalidParam(result);
		return userService.update(user);
	}
	
}
