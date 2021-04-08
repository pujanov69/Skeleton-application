/**
 * @author Pujan KC <pujanov69@gmail.com>
 * Since Aug 23, 2019
 */
package com.pujanov.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.pujanov.domain.User;
import com.pujanov.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private PasswordEncoder passwordEncoder;
	private UserService userService;
	
	public CustomAuthenticationProvider(PasswordEncoder passwordEncoder, UserService userService) {
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		User user;
		try {
			user = userService.getUserByUsername(token.getName());
			if(passwordEncoder.matches(token.getCredentials().toString(), user.getPassword())) {
				CustomUserDetails loggedInUser = new CustomUserDetails(user);
				authentication = new UsernamePasswordAuthenticationToken(loggedInUser, null, loggedInUser.getAuthorities());
				return authentication;
			} else {
				throw new BadCredentialsException("Bad Credentials");
			}
		} catch(Exception ex) {
			throw new UsernameNotFoundException(ex.getMessage());
		}
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}
