package com.agsft.ticketleap.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.agsft.ticketleap.model.Roles;
import com.agsft.ticketleap.model.User;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtUserFactory {

	public JwtUser create(User user) {
		return new JwtUser(user.get_id(), user.getEmail(), user.getFullName(), user.getPassword(),
				new ArrayList<GrantedAuthority>(), // mapToGrantedAuthorities(user.getAuthorities()),
				user.isRegistered());
	}

	@SuppressWarnings("unused")
	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Roles> authorities) {
		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getRoleType()))
				.collect(Collectors.toList());
	}
}
