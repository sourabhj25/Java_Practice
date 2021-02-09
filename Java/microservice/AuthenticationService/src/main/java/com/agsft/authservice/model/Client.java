/**
 * 
 */
package com.agsft.authservice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;
/**
 * @author nilesh
 *
 */
@Table(name="client")
@Entity
@Data
public class Client extends AuditBaseModel {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="client_id")
	int clientId;
	
	@Column(name="user_name")
	String userName;
	@Column(name="firstname")
	String firstName;
	@Column(name="lastname")
	String lastName;
	
	String password;
	
	@Column(name="email_id")
	String email;
	
	@Column(name="access_key")
	String accessKey;
	@Column(name="secret_key")
	String secretKey;
	
	boolean isExpirationEnabled;
	
	String timezone;
	
	long expirationTimeout;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinColumn(name = "security_id")
	SecurityType securityType;
	
	@OneToMany(mappedBy = "client")
	private List<Client> client;
	

}
