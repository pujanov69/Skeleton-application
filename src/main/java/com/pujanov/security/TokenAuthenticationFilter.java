/**
 * @author Pujan KC <pujanov69@gmail.com>
 * Since Aug 23, 2019
 */
package com.pujanov.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pujanov.domain.LoginDTO;
import com.pujanov.domain.User;
import com.pujanov.exception.domain.ApiError;
import com.pujanov.utility.JsonTokenHelper;

public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private ObjectMapper objectMapper;
	private JsonTokenHelper tokenHelper;
	
	public TokenAuthenticationFilter(
			AuthenticationManager authenticationManager,
			ObjectMapper objectMapper,
			JsonTokenHelper tokenHelper) {
		this.authenticationManager = authenticationManager;
		this.objectMapper = objectMapper;
		this.tokenHelper = tokenHelper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginDTO loginData = objectMapper.readValue(request.getInputStream(), LoginDTO.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginData.getUsername(),
							loginData.getPassword(),
							new ArrayList<>()
					));
		} catch (Exception ex) {
			throw new com.pujanov.domain.AuthException(ex.getMessage());
		}
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User user = new User();
		user = ((CustomUserDetails) authResult.getPrincipal()).getUser();
		response.setContentType("application/json");
		response.setStatus(HttpStatus.OK.value());
		String jwtToken = tokenHelper.generateToken(user);
		user.setToken(jwtToken);
		objectMapper.writeValue(response.getOutputStream(), user);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		if(!request.getMethod().equalsIgnoreCase("post")) {
			response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
			response.setContentType("application/json");
			objectMapper.writeValue(response.getOutputStream(), String.format("%s method is not allowed.", request.getMethod()));
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json");
			ApiError apiError = new ApiError.Builder(HttpStatus.UNAUTHORIZED)
					.withTime(LocalDateTime.now())
					.withMessage(failed.getLocalizedMessage())
					.withDescription("Authentication was not successful. Please re-enter correct credentials.")
					.build();
			objectMapper.writeValue(response.getOutputStream(), apiError);
		}
		
	}

	
}
