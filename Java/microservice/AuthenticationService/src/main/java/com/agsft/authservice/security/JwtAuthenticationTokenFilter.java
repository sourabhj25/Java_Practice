package com.agsft.authservice.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(this.tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        int clientId=jwtTokenUtil.getClientId(authToken);
        log.info("checking authentication for user {}", username);
        log.info("Get clientId from token:"+clientId);
             if (username != null && clientId>0&&SecurityContextHolder.getContext().getAuthentication() == null) {
             	UserDetails userDetails = this.userDetailsService.loadUserByUsername(username,clientId);
             	
                 // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
                 // the database compellingly. Again it's up to you ;)
                 if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                     UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                     authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                     log.info("authenticated user {} {} ", username, ", setting security context");
                     SecurityContextHolder.getContext().setAuthentication(authentication);
                 }
             }
       
        chain.doFilter(request, response);
    }
    
}