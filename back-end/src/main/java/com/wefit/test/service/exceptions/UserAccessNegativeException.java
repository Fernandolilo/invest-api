package com.wefit.test.service.exceptions;

public class UserAccessNegativeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAccessNegativeException(String message) {
		super(message);
	}

	public UserAccessNegativeException(String message, Throwable cause) {
		super(message, cause);
	}
}
