package com.epam.idea.core.service.exception;

public class UserNotFoundException extends RuntimeException {

	public static final String ERROR_MSG_PATTERN_USER_NOT_FOUND = "Could not find user with id: %s.";

	public UserNotFoundException(Long userId) {
		super(String.format(ERROR_MSG_PATTERN_USER_NOT_FOUND, userId));
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}
