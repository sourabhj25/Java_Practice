/**
 * 
 */
package com.agsft.authservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;

/**
 * @author nilesh
 *
 */
@Table
@Entity(name="user_token")
@Data
public class UserToken extends AuditBaseModel{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int userTokenId;
	
	String token;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToOne
	@JoinColumn(name = "token_type")
	UserTokenType tokenType;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	User user;
	
}
