package com.agsft.ticketleap.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Vishal
 *
 */
public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final String id;
	private final String username;
	private final String fullname;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;
	private final boolean isRegistered;

	public JwtUser(String id, String username, String fullname, String password,
			Collection<? extends GrantedAuthority> authorities, boolean isRegistered) {
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.password = password;
		this.authorities = authorities;
		this.isRegistered = isRegistered;
	}

	@JsonIgnore
	public String getId() {
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

	public String getFullname() {
		return fullname;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean isRegistered() {
		return isRegistered;
	}

}
