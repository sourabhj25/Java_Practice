package com.agsft.microservice.utility;


import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.java.Log;

@Component
@Log
public class JwtTokenUtility implements Serializable {

	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtility.class);
    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_CREATED = "created";
    
    @Value("${jwt.secret}")
    private String secret;

    public Claims getClaimsFromToken(String token) {
        Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        return claims;
    }

    
    public Date getExpirationDateTimeFromToken(String token) {
        Date expirationDate=null;
           final Claims claims = getClaimsFromToken(token);
           if(claims!=null)
            expirationDate = claims.getExpiration();
         
        return expirationDate;
    }
 
    public Boolean isTokenExpired(String token) {
    	try
    	{
        final Date expirationDate = getExpirationDateTimeFromToken(token);
        if(expirationDate==null)
            return false;
        else
        log.info("expiration time"+expirationDate+"current time"+new Date());
        return expirationDate.before(new Date());
    	}
    	catch(Exception e)
    	{
    		logger.info("token expired exception:"+e.getMessage());
    		return true;
    	}
        
    }
    
   
    
}