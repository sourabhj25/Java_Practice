/**
 * 
 */
package com.agsft.authservice.model.listener;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.agsft.authservice.model.User;

import lombok.extern.java.Log;

/**
 * @author nilesh
 *
 */
@Log
public class UserListener {

	@PrePersist
	public void userPrePersist(User ob) {
		log.info("Listening User Pre Persist : " + ob.getUsername());
	}
	@PostPersist
	public void userPostPersist(User ob) {
		log.info("Listening User Post Persist : " + ob.getUsername());
	}
	@PostLoad
	public void userPostLoad(User ob) {
		log.info("Listening User Post Load : " + ob.getUsername());
	}	
	@PreUpdate
	public void userPreUpdate(User ob) {
		log.info("Listening User Pre Update : " + ob.getUsername());
	}
	@PostUpdate
	public void userPostUpdate(User ob) {
		log.info("Listening User Post Update : " + ob.getUsername());
	}
	@PreRemove
	public void userPreRemove(User ob) {
		log.info("Listening User Pre Remove : " + ob.getUsername());
	}
	@PostRemove
	public void userPostRemove(User ob) {
		log.info("Listening User Post Remove : " + ob.getUsername());
	}
	
}
