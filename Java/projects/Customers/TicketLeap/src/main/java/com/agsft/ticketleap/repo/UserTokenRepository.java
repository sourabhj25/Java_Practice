package com.agsft.ticketleap.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.UserToken;

/**Token repository to persist token to DB
 * 
 * @author shekhar
 *
 */
@Repository
public interface UserTokenRepository extends MongoRepository<UserToken, String> {

	@Query("{ '_id' : ?0 }")
	UserToken findUserById(String _id);

	@Query("{ 'token' : ?0 }")
	UserToken findUserByToken(String token);

}
