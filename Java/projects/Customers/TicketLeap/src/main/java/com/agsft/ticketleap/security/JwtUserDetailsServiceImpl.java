package com.agsft.ticketleap.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.repo.UserRepository;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findUserByEmail(email);

		if (user == null)
			throw new UsernameNotFoundException(String.format("No user exists with email : %s", email));
		else
			return JwtUserFactory.create(user);
	}

	public User getUserByEmail(String email) throws UsernameNotFoundException {
		User user = userRepo.findUserByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
		} else {
			return user;
		}
	}

}
