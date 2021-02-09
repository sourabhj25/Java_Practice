/**
 * 
 */
package com.agsft.authservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agsft.authservice.model.Client;

/**
 * @author nilesh
 *
 */
@Repository
public interface ClientRepository extends CrudRepository<Client, Integer>{

	
	  @Query(value = "SELECT * FROM client where access_key=?1", nativeQuery = true)
	  Client findClientByAccessKey(String accessKey);
	  
}
