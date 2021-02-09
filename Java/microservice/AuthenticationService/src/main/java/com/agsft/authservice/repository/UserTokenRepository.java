/**
 * 
 */
package com.agsft.authservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agsft.authservice.model.UserToken;

/**
 * @author nilesh
 *
 */
@Repository
public interface UserTokenRepository extends CrudRepository<UserToken, Integer>{
	
	@Query(value = "SELECT * FROM user_token where token=?1 and user_id=?2", nativeQuery = true)
	UserToken findUserToken(String token,int user_id);
}
