package br.com.softcare.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.softcare.entities.User;

public interface UserRepository extends CrudRepository<User,Long> {

	public User findByEmailAndPassword(String email,String password);
	
	public User findByEmail(String email);
	
	public User findByToken(String token);
	
}
