package com.agsft.ticketleap.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.agsft.ticketleap.model.Event;
import com.agsft.ticketleap.model.Organisation;

/**
 * OrganisationRepository to perform relevant DB operations
 * 
 * @author Vishal
 *
 */
@Repository
public interface OrganisationRepository extends MongoRepository<Organisation, String>{

	@Query("{ '_id' : ?0 }")
	Organisation findOrgById(String _id);
	
	@Query("{ 'orgName' : ?0 }")
	Organisation findOrgByName(String orgName);
	
	@Query("{ 'orgUrl' : ?0 }")
	Organisation findOrgByUrl(String orgUrl);
	
	@Query("{ 'partnetId' : ?0 }")
	List<Organisation> findOrgByParentId(String parentId);

	
}
