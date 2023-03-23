package com.example.gateway.exception;


public class GatewayException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public GatewayException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
	public GatewayException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
