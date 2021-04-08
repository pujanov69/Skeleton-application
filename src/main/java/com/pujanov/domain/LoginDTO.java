/**
 * @author Pujan KC <pujanov69@gmail.com>
 * Since Aug 23, 2019
 */
package com.pujanov.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginDTO {
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
