package com.agsft.authservice.security;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.agsft.authservice.model.Client;
import com.agsft.authservice.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;


@Component
@Log
public class JwtTokenUtil implements Serializable {

	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final long serialVersionUID = -3301605591108950415L;
    public static final String DATE_FORMATE_YY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm:ss";
    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "created";
    static final String CLAIM_KEY_CLIENTID = "clientID";
    static final String CLAIM_KEY_ISEXPIRATION_ENABLED = "isExpirationEnabled";
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Value("${jwt.expiration.enabled}")
	private boolean expirationEnabled;

 
    public String getUsernameFromToken(String token) {
        
    	String username;
         try
         {
            final Claims claims = getClaimsFromToken(token);
            if(claims!=null)
            username = claims.getSubject();
            else
            username=null;
         }catch(IllegalArgumentException e)
         {
        	 username=null;
        	 logger.error("Illegal argument:"+e);
         }
        return username;
    }
    
     /**
      * Used to get created timestamp from generated token
      * @param token
      * @return created date
      */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            if(claims!=null)
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
            else
            created=null;
        } catch (Exception e) {
            created = null;
            logger.error("created date exipred:"+e);
        }
        return created;
    }
    
    /**
     * Used to get client id from generated token
     * @param token
     * @return
     */
    public int getClientId(String token) {
       int clientId=0;
        try {
            final Claims claims = getClaimsFromToken(token);
            if(claims!=null)
            	clientId = (int) claims.get(CLAIM_KEY_CLIENTID);
        } catch (Exception e) {
 
            logger.error("created date exipred:"+e);
        }
        return clientId;
    }
    
    /**
     * Used to get token expiration is enabled or not
     * @param token
     * @return
     */
    public boolean isExpirationEnabled(String token) {
        boolean isExpirationEnabled=false;
         try {
             final Claims claims = getClaimsFromToken(token);
             if(claims!=null)
            	 isExpirationEnabled = (boolean) claims.get(CLAIM_KEY_ISEXPIRATION_ENABLED);
         } catch (Exception e) {
  
             logger.error("created date exipred:"+e);
         }
         return isExpirationEnabled;
     }
     
    /**
     * Used to get expiration timestamp from token
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        Date expirationDate;
        try {
            final Claims claims = getClaimsFromToken(token);
            if(claims!=null)
            expirationDate = claims.getExpiration();
            else
            expirationDate=null;	
        }
        catch (Exception e) {
            expirationDate = null;
            logger.error("Expiration time failed:"+e);
        }
        return expirationDate;
    }

   
    private Claims getClaimsFromToken(String token) {
        Claims claims;
            try
            {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            }
            catch(io.jsonwebtoken.ExpiredJwtException | io.jsonwebtoken.MalformedJwtException e)
            {
            	claims=null;
            	logger.error("JWT expired:"+e);
            }
        return claims;
    }

    private Date generateExpirationDate(Long expirationTimeout) {
        return new Date(System.currentTimeMillis() + expirationTimeout * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date exp = getExpirationDateFromToken(token);
        return exp.before(new Date());
    }
    
   
    public String refreshToken(String token,Long expirationTimeout) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            if(isTokenExpired(token))
            {
            	refreshedToken=token;
            }
            else
            {	
            if(claims!=null)
            {
            claims.put(CLAIM_KEY_CREATED, new Date());
            }
            refreshedToken = generateClaimsToken(claims,true,expirationTimeout);
            }
        } catch (Exception e) 
        {
            refreshedToken = null;
            logger.error("JWT token exipred"+e);    
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        if(isExpirationEnabled(token))
        {
        	return username.equalsIgnoreCase(user.getUsername())
            && !isTokenExpired(token);
        }	
        return 
          username.equalsIgnoreCase(user.getUsername());
    }
    
    /**
     * Used to get current date as per timezone
     * @param timezone
     * @return
     */
    public Date getCurrentTimeByTimeZone(String timezone)
    {
    	logger.info("Get current time with timezone"+timezone);
    	try 
	    {
    	SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATE_YY_MM_DD_HH_MM);
		formatter.setTimeZone(TimeZone.getTimeZone(timezone));
		Date date = new Date();
		return parseDate(formatter.format(date.getTime()));
	    }
    	catch(Exception e)
    	{	
    		logger.error("failed generate current timestamp with timezone"+e.getMessage());
    		//return default application timezone
    		return new Date();
    	}
    }
    /**
     * Used to parsed string date to date timestamp
     * @param date
     * @return
     * @throws ParseException
     */
    public Date parseDate(String date) throws ParseException {
		logger.info("Inside parseDate method");
		// convert string date to Date
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATE_YY_MM_DD_HH_MM);
		return formatter.parse(date);
	}
    
    /**
     * Generated timestamp as per user and client details
     * Token generated using fields username,current timestamp, user clientId ,client token expiration enabled or not
     * @param user
     * @param client
     * @return
     */
    public String generateToken(User user,Client client) {
       Map<String, Object> claims = new HashMap<>();
       claims.put(CLAIM_KEY_USERNAME, user.getUsername());
       // Date tokenCreatedAt= getCurrentTimeByTimeZone(client.getTimezone());
       Date tokenCreatedAt=new Date();
       log.info("Token create at"+tokenCreatedAt+" with timeout:"+client.getExpirationTimeout()+" with time zone:"+client.getTimezone());
       claims.put(CLAIM_KEY_CREATED, tokenCreatedAt);
       claims.put(CLAIM_KEY_CLIENTID,client.getClientId());
       claims.put(CLAIM_KEY_ISEXPIRATION_ENABLED, client.isExpirationEnabled());
       return generateClaimsToken(claims,client.isExpirationEnabled(),client.getExpirationTimeout());
    }
    
    /**
     * Generate token as per claims object details
     * If claims has expiration enabled then generate token with expiration time
     * @param claims
     * @param expirationEnabled
     * @param expirationTimeout
     * @return
     */
    String generateClaimsToken(Map<String, Object> claims,boolean expirationEnabled,Long expirationTimeout) 
    {
		if (expirationEnabled) {
			return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate(expirationTimeout))
					.signWith(SignatureAlgorithm.HS512, secret).compact();
		} else {
			return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
		}

	}
    
}