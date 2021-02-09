package com.agsft.authservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agsft.authservice.model.UserTokenType;

@Repository
public interface UserTokenTypeRepository extends CrudRepository<UserTokenType, Integer>{

	 
	
}
