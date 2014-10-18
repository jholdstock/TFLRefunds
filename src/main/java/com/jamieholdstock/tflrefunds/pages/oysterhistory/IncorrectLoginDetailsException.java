package com.jamieholdstock.tflrefunds.pages.oysterhistory;

public class IncorrectLoginDetailsException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String message;
	public IncorrectLoginDetailsException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
