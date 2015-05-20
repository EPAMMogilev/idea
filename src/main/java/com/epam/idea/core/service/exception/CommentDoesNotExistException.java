package com.epam.idea.core.service.exception;

public class CommentDoesNotExistException extends RuntimeException {

	public CommentDoesNotExistException() {
	}

	public CommentDoesNotExistException(String message) {
		super(message);
	}

	public CommentDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommentDoesNotExistException(Throwable cause) {
		super(cause);
	}
}
