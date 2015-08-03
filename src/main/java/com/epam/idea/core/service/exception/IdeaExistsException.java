package com.epam.idea.core.service.exception;

public class IdeaExistsException extends RuntimeException {

	public IdeaExistsException() {
	}

	public IdeaExistsException(String message) {
		super(message);
	}

	public IdeaExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public IdeaExistsException(Throwable cause) {
		super(cause);
	}
}
