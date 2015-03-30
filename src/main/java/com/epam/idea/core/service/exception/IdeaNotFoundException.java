package com.epam.idea.core.service.exception;

public class IdeaNotFoundException extends RuntimeException {
	
	public static final String ERROR_MSG_PATTERN_IDEA_NOT_FOUND = "Could not find idea with id: %s.";
	
	public IdeaNotFoundException(Long ideaId) {

		super(String.format(ERROR_MSG_PATTERN_IDEA_NOT_FOUND, ideaId));
	}

	public IdeaNotFoundException(String message) {
		super(message);
	}

	public IdeaNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public IdeaNotFoundException(Throwable cause) {
		super(cause);
	}
}
