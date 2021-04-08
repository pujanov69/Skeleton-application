/**
 * @author Pujan KC <pujanov69@gmail.com>
 * Since Aug 23, 2019
 */
package com.pujanov.utility;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.pujanov.domain.User;
import com.pujanov.security.CustomUserDetails;
import com.pujanov.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JsonTokenHelper {
	private final String KEY;
	private final String APPLICATION_NAME;
	private final String TOKEN_HEADER;
	private final String TOKEN_SCHEMA_PREFIX;
	private final int EXPIRATION_TIME;
	private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
	private UserService userService;

	public JsonTokenHelper(@Value("${jwt.security.key}") String KEY,
			@Value("${jwt.applicationName}") String APPLICATION_NAME,
			@Value("${jwt.auth.header}") String TOKEN_HEADER,
			@Value("${jwt.auth.schema}") String TOKEN_SCHEMA_PREFIX,
			@Value("${jwt.expirationDate}") int EXPIRATION_TIME,
			UserService userService) {
		this.KEY = KEY;
		this.APPLICATION_NAME = APPLICATION_NAME;
		this.TOKEN_HEADER = TOKEN_HEADER;
		this.TOKEN_SCHEMA_PREFIX = TOKEN_SCHEMA_PREFIX;
		this.EXPIRATION_TIME = EXPIRATION_TIME;
		this.userService = userService;
	}

	/**
	 * This method generates the json web token and returns it to the caller.
	 * @param user: User
	 * @return String
	 */
	public String generateToken(User user) {
		String token = Jwts.builder()
				.claim("role", "ADMIN")
				.setSubject(user.getUsername())
				.setAudience("ISHANITECH_CLIENTS")
				.setIssuer(APPLICATION_NAME)
				.setIssuedAt(generateExpirationDate())
				.setExpiration(generateExpirationDate())
				.signWith(SIGNATURE_ALGORITHM, KEY).compact();

		return token;
	}
	
	/**
	 * This method extracts and returns the json web token from request object.
	 * @param request
	 * @return token: String
	 */
	public String resolveToken(HttpServletRequest request) {
		String tokenWithBearerSchema = request.getHeader(TOKEN_HEADER);
		if(tokenWithBearerSchema != null && tokenWithBearerSchema.startsWith("Bearer ")) {
			String token = tokenWithBearerSchema.substring(7, tokenWithBearerSchema.length());
			try {
				if(validateToken(token)) {
					return token;
				}
			} catch(JwtException ex) {
				throw new JwtException(ex.getMessage());
			}
		}
		
		return null;
	}
	
	/**
	 * This method validates the jwt token expiration date...
	 * @param token
	 * @return boolean
	 */
	public boolean validateToken(String token) {
		try {
			Claims claim = getClaimsFromToken(token);
			if(claim.getExpiration().before(new Date())) {
				return false;
			}
			
			return true;
		} catch(JwtException| IllegalArgumentException ex) {
			throw new JwtException("Invalid or expired token.");
		}
	}
	
	
	/**
	 * This method extracts claims from jwt token.
	 * @param token
	 * @return
	 */
	public Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(KEY)
					.parseClaimsJws(token)
					.getBody();
		} catch(Exception ex) {
			throw new JwtException(ex.getMessage());
		}
		
		return claims;
	}
	
	/**
	 * This method extracts and returns username from json web token...
	 * @param token
	 * @return username: String
	 */
	public String getUsernameFromToken(String token) {
		String username;
		try {
			username = this.getClaimsFromToken(token).getSubject();
		} catch(Exception ex) {
			throw new JwtException(ex.getMessage());
		}
		
		return username;
	}
	
	/**
	 * This method extracts user from token and creates and returns UsernamePasswordAuthenticationToken object
	 * which is used by spring security.
	 * @param request
	 * @return usernamePasswordAuthenticationToken: UsernamePasswordAuthenticationToken
	 */
	public UsernamePasswordAuthenticationToken generateUsernamePasswordAuthenticationTokenForValidToken(HttpServletRequest request) {
		String token = this.resolveToken(request);
		if(token != null) {
			String username = this.getUsernameFromToken(token);
			if(username != null) {
				CustomUserDetails user = new CustomUserDetails(userService.getUserByUsername(username));
				return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			}
			return null;
		}
		return null;
	}
	/**
	 * Returns the current date.
	 * @return Date
	 */
	private Date generateCurrentDate() {
		return new Date();
	}

	/**
	 * Generates and returns the token expiration date...
	 * @return  expirationDate: Date
	 */
	private Date generateExpirationDate() {
		return new Date(generateCurrentDate().getTime() + this.EXPIRATION_TIME);
	}
}
