package com.agsft.ticketleap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.agsft.ticketleap.model.Event;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.repo.EventRepository;
import com.agsft.ticketleap.repo.UserRepository;
import com.agsft.ticketleap.services.UserEventRegisteration;

/**
 * @author Vishal
 *
 */
@Component
public class UserEventRegisterationImpl implements UserEventRegisteration {

	@Autowired
	UserRepository userRepo;

	@Autowired
	EventRepository eventRepo;

	@Autowired
	MongoOperations mongoOperations;

	@Override
	public void registerUserToAnEvent(String userId, String eventId) {

		/**
		 * Update user id inside event
		 * 
		 */
		Update update = new Update();
		update.push("registeredUsers", userId);

		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(eventId));

		mongoOperations.upsert(query, update, Event.class);

		/**
		 * Update event id inside user
		 * 
		 */
		Update update2 = new Update();
		update2.push("registerEventsIDs", eventId);

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("_id").is(userId));

		mongoOperations.upsert(query2, update2, User.class);

	}

}
