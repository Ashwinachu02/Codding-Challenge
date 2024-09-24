package com.springboot.automob.exception;

public class ExecutiveValidationException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String message;
	public ExecutiveValidationException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
