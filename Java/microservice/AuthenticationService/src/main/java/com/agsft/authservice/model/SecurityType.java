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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author nilesh
 *
 */
@Table
@Entity(name="security_type")
public class SecurityType {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int securityTypeId;
	
	@Column(name="security_type")
	String securityType;
	
	@OneToMany(mappedBy = "securityType")
	List<Client> client;
	
	
	
}
