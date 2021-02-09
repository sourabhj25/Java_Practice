package com.agsft.ticketleap.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.agsft.ticketleap.model.Event;

/**
 * EventRepository to perform relevant DB operations
 * 
 * @author Vishal
 *
 */
@Repository
public interface EventRepository extends MongoRepository<Event, String>{

	@Query("{ '_id' : ?0 }")
	Event findEventById(String _id);
	
	@Query("{ 'eventName' : ?0 }")
	Event findEventByName(String eventName);
	
	@Query("{ 'eventUrl' : ?0 }")
	Event findEventByUrl(String eventUrl);
	
	@Query("{'orgnisationName' : ?0 }")
	List<Event> findEventByOrgName(String orgnisationName);
	
}
