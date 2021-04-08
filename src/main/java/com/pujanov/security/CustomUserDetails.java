/**
 * @author Pujan KC <pujanov69@gmail.com>
 * Since Aug 27, 2019
 */
package com.pujanov.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pujanov.domain.User;

public class CustomUserDetails implements UserDetails{
	private static final long serialVersionUID = 6534708822085674206L;
	private User user;
	private final List<GrantedAuthority> authorities;
	
	public CustomUserDetails(User user) {
		super();
		this.user = user;
		this.authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
					user.getRole().stream()
						.map(role -> role.getRole())
						.collect(Collectors.joining(", "))
				);
	
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.user.isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return this.user.isEnabled();
	}

	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "CustomUserDetails [user=" + this.user.toString() + ", authorities=" + authorities + "]";
	}

	
}
