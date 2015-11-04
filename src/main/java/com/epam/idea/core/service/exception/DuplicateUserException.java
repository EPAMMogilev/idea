package com.epam.idea.core.service.exception;

public class DuplicateUserException extends RuntimeException {

	public static final String ERROR_MSG_PATTERN_DUPLICATE_USER = "User with email \"%s.\" already exist";

	public DuplicateUserException(String email) {
		super(String.format(ERROR_MSG_PATTERN_DUPLICATE_USER, email));
	}

	public DuplicateUserException(Throwable cause) {
		super(cause);
	}

	public DuplicateUserException(String message, Throwable cause) {
		super(message, cause);
	}

//	public DuplicateUserException(String message) {
//		super(message);
//	}
}
