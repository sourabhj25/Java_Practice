package com.agsft.authservice.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.agsft.authservice.model.listener.UserListener;

import lombok.Data;

@Table
@Entity(name="user_token_type")
@Data
public class UserTokenType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int tokenTypeId;
	
	String tokenType;
	
	@OneToMany(mappedBy = "tokenType")
	private List<UserToken> tokens;
	
	
}
