package com.bluurr.quora.page.login;

public class InvalidLoginException extends IllegalStateException {
	public InvalidLoginException(final String message) {
		super(message);
	}
}
