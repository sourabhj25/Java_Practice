package com.agsft.authservice.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.agsft.authservice.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final int id;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String password;
    private final String email;
   // private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final int clientId;
    

    public JwtUser(
          int id,
          String username,
          String firstname,
          String lastname,
          String email,
          String password,
          boolean enabled,
          int clientId
    ) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.clientId=clientId;
    }

    @JsonIgnore
    public int getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean isEnabled() {
        return enabled;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getClientId() {
		return clientId;
	}

	public static JwtUser create(User user) {
        return new JwtUser(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.isActive(),
                user.getClient().getClientId()
        );
	}

   
}
