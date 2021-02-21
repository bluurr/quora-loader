package com.bluurr.quora.domain.user;

public class InvalidLoginException extends IllegalStateException {
	public InvalidLoginException(final String message) {
		super(message);
	}
}
