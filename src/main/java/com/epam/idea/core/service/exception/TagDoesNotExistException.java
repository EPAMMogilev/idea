package com.epam.idea.core.service.exception;

public class TagDoesNotExistException extends RuntimeException {

	public static final String ERROR_MSG_PATTERN_TAG_NOT_FOUND = "Could not find tag";

	public TagDoesNotExistException() {
		super(ERROR_MSG_PATTERN_TAG_NOT_FOUND);
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
