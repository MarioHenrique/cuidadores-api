package br.com.softcare.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.softcare.entities.User;

@Repository
public interface CareGiverRepository extends CrudRepository<User,Long>,JpaSpecificationExecutor<User>{

	@Query("select u from usr_api u where u.id=:id")
	public User findById(@Param("id") Long id);
	
}
