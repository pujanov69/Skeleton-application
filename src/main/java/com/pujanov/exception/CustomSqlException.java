/**
 * @author Pujan KC <pujanov69@gmail.com>
 */
package com.pujanov.exception;

import org.jdbi.v3.core.JdbiException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomSqlException extends JdbiException {
	private static final long serialVersionUID = 6516144753054576629L;
	private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	/**
	 * @param message
	 */
	public CustomSqlException(String message) {
		super(message);
	}
}
