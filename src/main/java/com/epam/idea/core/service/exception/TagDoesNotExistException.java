package com.epam.idea.core.service.exception;

public class TagDoesNotExistException extends RuntimeException {

	public TagDoesNotExistException() {
	}

	public TagDoesNotExistException(String message) {
		super(message);
	}

	public TagDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public TagDoesNotExistException(Throwable cause) {
		super(cause);
	}
}
