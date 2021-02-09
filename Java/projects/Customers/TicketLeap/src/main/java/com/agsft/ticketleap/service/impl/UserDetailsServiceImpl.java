/*package com.agsft.ticketleap.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.repo.UserRepository;



// TODO: Auto-generated Javadoc
*//**
 * The Class UserDetailsServiceImpl.
 *//*
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	*//** The user repository. *//*
	@Autowired
	private UserRepository userRepository;

	
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		User user = userRepository.findUserByEmail(emailId);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		
			grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				grantedAuthorities);
	}
	
	public UserDetails loadUserByEmailId(String emailId) throws UsernameNotFoundException {
		User user = userRepository.findUserByEmail(emailId);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				grantedAuthorities);
	}
	
	
}
*/