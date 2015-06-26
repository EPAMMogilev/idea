package com.epam.idea.core.service.exception;

public class RoleDoesNotExistException extends RuntimeException {

	public RoleDoesNotExistException() {
	}

	public RoleDoesNotExistException(String message) {
		super(message);
	}

	public RoleDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoleDoesNotExistException(Throwable cause) {
		super(cause);
	}
}
