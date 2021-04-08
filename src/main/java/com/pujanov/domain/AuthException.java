/**
 * @author Pujan KC <pujanov69@gmail.com>
 * Since Aug 23, 2019
 */
package com.pujanov.domain;
import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	/**
	 * @param msg
	 */
	public AuthException(String msg) {
		super(msg);
	}

}
