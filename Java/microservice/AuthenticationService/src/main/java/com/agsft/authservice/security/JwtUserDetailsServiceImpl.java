package com.agsft.authservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.agsft.authservice.model.User;
import com.agsft.authservice.repository.UserRepository;


/**
 * Created by nilesh
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUser.create(user);
        }
    }
    
    /**
     * This customize user user details method used t generate user details object
     * @param username
     * @param clientId
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String username,int clientId) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsernameAndClient(username, clientId);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUser.create(user);
        }
    }
    
    
    
   
}
