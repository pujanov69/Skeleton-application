/**
 * @author Pujan KC <pujanov69@gmail.com>
 * Since Sep 1, 2019
 */
package com.pujanov.exception;

import java.time.LocalDateTime;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pujanov.exception.domain.ApiError;
import com.pujanov.exception.domain.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		return super.handleMissingPathVariable(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError.Builder(HttpStatus.BAD_REQUEST)
					.withTime(LocalDateTime.now())
					.withMessage("Some request parameter are missing in request object...")
					.withDescription("")
					.build();
		return buildErrorResponse(apiError);
	}
	
	

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError.Builder(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
				.withMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
				.withDescription(ex.getContentType() + " is not supported..")
				.withTime(LocalDateTime.now())
				.build();
		return buildErrorResponse(apiError);
	}
	
	

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError.Builder(HttpStatus.BAD_REQUEST)
					.withMessage("Validation Error.")
					.withDescription(ex.getBindingResult().getAllErrors().toString())
					.build();
		return buildErrorResponse(apiError);
	}
	
	

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError.Builder(HttpStatus.BAD_REQUEST)
				.withMessage("Malformed json request.")
				.withDescription("")
				.withTime(LocalDateTime.now())
				.build();
		return buildErrorResponse(apiError);
	}
	
	

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError.Builder(HttpStatus.INTERNAL_SERVER_ERROR)
				.withTime(LocalDateTime.now())
				.withMessage("Error occured while writing json output.")
				.withDescription("")
				.build();
		return buildErrorResponse(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError.Builder(HttpStatus.BAD_REQUEST)
				.withTime(LocalDateTime.now())
				.withMessage("No handler found..")
				.withDescription(String.format("Couldn't find %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()))
				.build();
		return buildErrorResponse(apiError);
	}
	
	@ExceptionHandler(javax.validation.ConstraintViolationException.class) 
	public ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
		ApiError apiError = new ApiError.Builder(HttpStatus.BAD_REQUEST)
				.withTime(LocalDateTime.now())
				.withMessage("Validation error.")
				.withDescription("Violations: " + ex.getConstraintViolations().toString())
				.build();
		return buildErrorResponse(apiError);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
		ApiError apiError = new ApiError.Builder(HttpStatus.NOT_FOUND)
				.withTime(LocalDateTime.now())
				.withMessage(ex.getMessage())
				.build();
		return buildErrorResponse(apiError);
	}

	/**
	 * 
	 * @param apiError ApiError class which represents the error
	 * @return ResponseEntity object
	 */
	private ResponseEntity<Object> buildErrorResponse(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
