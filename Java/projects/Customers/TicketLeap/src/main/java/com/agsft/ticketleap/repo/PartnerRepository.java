package com.agsft.ticketleap.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.agsft.ticketleap.model.Partner;

/**
 * PartnerRepository to perform relevant DB operations
 * 
 * @author Vishal
 *
 */
@Repository
public interface PartnerRepository extends MongoRepository<Partner, String>{

	@Query("{ 'partnerName' : ?0 }")
	Partner findByName(String partnerName);
	
}
