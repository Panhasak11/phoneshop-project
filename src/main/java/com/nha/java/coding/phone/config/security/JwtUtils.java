package com.nha.java.coding.phone.config.security;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {

	 @Value("${app.jwtSecret}")
	  private String jwtSecret;

	  @Value("${app.jwtExpirationMs}")
	  private int jwtExpirationMs;

	  public String generateJwtToken(String username, List<String> roles) {
	    return Jwts.builder()
	        .setSubject(username)
	        .claim("authorities", roles)
	        .setIssuedAt(new Date())
	        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
	        .signWith(key(), SignatureAlgorithm.HS256)
	        .compact();
	  }

	  private Key key() {
	    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	  }

	  public String getUserNameFromJwtToken(String token) {
	    return Jwts.parserBuilder()
	    		.setSigningKey(key())
	    		.build()
	            .parseClaimsJws(token)
	            .getBody()
	            .getSubject();
	  }
	  
	  public List<String> getRolesFromJwtToken(String token) {
	        Claims  claims = Jwts.parserBuilder()
	            .setSigningKey(key())
	            .build()
	            .parseClaimsJws(token)
	            .getBody();
	        
	        Object roles = claims.get("roles");
	        if (roles instanceof List<?>) {
	            return ((List<?>) roles).stream()
	                    .filter(String.class::isInstance)
	                    .map(String.class::cast)
	                    .toList();
	        }

	        return List.of();
	    }

	  public boolean validateJwtToken(String authToken) {
	    try {
	      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
	      return true;
	    } catch (MalformedJwtException e) {
	      log.error("Invalid JWT token: {}", e.getMessage());
	    } catch (ExpiredJwtException e) {
	    	log.error("JWT token is expired: {}", e.getMessage());
	    } catch (UnsupportedJwtException e) {
	    	log.error("JWT token is unsupported: {}", e.getMessage());
	    } catch (IllegalArgumentException e) {
	    	log.error("JWT claims string is empty: {}", e.getMessage());
	    }

	    return false;
	  }
	
}
