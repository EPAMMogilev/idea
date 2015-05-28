package com.epam.idea.core.service.exception;

public class UserSessionNotFoundException extends RuntimeException {

	public static final String ERROR_MSG_PATTERN_USER_SESSION_NOT_FOUND = "Could not find session with id: %s.";

	public UserSessionNotFoundException(String sessionId) {
		super(String.format(ERROR_MSG_PATTERN_USER_SESSION_NOT_FOUND, sessionId));
	}

	public UserSessionNotFoundException(Throwable cause) {
		super(cause);
	}

	public UserSessionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
