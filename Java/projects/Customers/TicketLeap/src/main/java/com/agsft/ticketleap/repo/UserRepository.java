package com.agsft.ticketleap.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.agsft.ticketleap.model.User;

/**
 * User Repository to perform relevant DB operations
 * 
 * @author Vishal
 *
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

	@Query("{ '_id' : ?0 }")
	User findUserById(String _id);
	
	@Query("{ 'fullName' : ?0 }")
	User findUserByFullName(String fullName);
	
	@Query("{ 'email' : ?0 }")
	User findUserByEmail(String email);
	
	@Query("{ $and : [{ 'email' : ?0 }, { 'password' : ?1 }] }")
	User checkLogin(String email, String password);
	
	@Query("{ $and : [{ 'partnerId' : ?0 }, { 'roles.roleType' : ?1 }] }")
	User getOwner(String partnerId, String roleType);
	
	@Query("{ $and : [{ 'organisationIds' : ?0 }]}")
	List<User> getUsersByOrganisation(String organisationId);
	
}
