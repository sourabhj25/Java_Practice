/**
 * 
 */
package com.agsft.authservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.agsft.authservice.model.User;


/**
 * @author nilesh
 *
 */
public interface UserRepository extends CrudRepository<User, Integer>{

	@Query(value = "SELECT * FROM user where username=?1 and client_id=?2", nativeQuery = true)
	User findByUsernameAndClient(String username,int clientId);
	
	User findByUsername(String username);
}
