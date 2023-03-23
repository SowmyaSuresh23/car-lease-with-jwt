package com.example.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomerExceptionHandler {

	@ExceptionHandler(GatewayException.class)
	public ResponseEntity<String> handleValidationError(final GatewayException exception,
			final HttpServletRequest request) {


		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
	}

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleValidationError(final Exception exception,
			final HttpServletRequest request) {


		return ResponseEntity.internalServerError().body(exception.getMessage());
	}

}
