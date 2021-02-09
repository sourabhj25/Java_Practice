/**
 * 
 */
package com.agsft.authservice.model;

/**
 * @author nilesh
 *
 */
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.agsft.authservice.model.listener.UserListener;

import lombok.Data;
// TODO: Auto-generated Javadoc
/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Data
@EntityListeners(UserListener.class)
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends AuditBaseModel implements Serializable  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int userId;

	/** The first name. */
	@Column(name = "first_name")
	private String firstName;

	/** The last login. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login")
	private Date lastLogin;

	/** The last name. */
	@Column(name = "last_name")
	private String lastName;

	/** The password. */
	private String password;
	
	private String email;
	
	@Column(name = "is_active")
	private boolean isActive;
		
	/** The username. */
	private String username;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@OneToMany(mappedBy = "user")
	private List<UserToken> tokens;
	
	/** The roles. */
	/*// bi-directional many-to-many association to Role
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Role roles;*/
  
	
}
