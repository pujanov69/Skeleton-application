/**
 * @author Pujan KC <pujanov69@gmail.com>
 * Since Aug 23, 2019
 */
package com.pujanov.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pujanov.utility.JsonTokenHelper;

import io.jsonwebtoken.JwtException;

public class TokenAuthorizationFilter extends OncePerRequestFilter {
	private JsonTokenHelper tokenHelper;
	private ObjectMapper objectMapper;

	public TokenAuthorizationFilter(JsonTokenHelper tokenHelper, ObjectMapper objectMapper) {
		this.tokenHelper = tokenHelper;
		this.objectMapper = objectMapper;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if (token == null || !token.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		try {
			UsernamePasswordAuthenticationToken authenticationObject = tokenHelper
					.generateUsernamePasswordAuthenticationTokenForValidToken(request);
			SecurityContextHolder.getContext().setAuthentication(authenticationObject);
			filterChain.doFilter(request, response);
			return;
		} catch (JwtException ex) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json");
			objectMapper.writeValue(response.getWriter(), ex.getMessage());
		}
		
	}

}
