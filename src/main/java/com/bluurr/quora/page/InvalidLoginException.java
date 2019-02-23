package com.bluurr.quora.page;

/**
 * 
 * @author Bluurr
 *
 */
public class InvalidLoginException extends IllegalStateException {
	private static final long serialVersionUID = -7955342082753968164L;

	public InvalidLoginException(final String message) {
		super(message);
	}
}
