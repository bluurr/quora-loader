package com.bluurr.quora.model.user;

public class InvalidLoginException extends IllegalStateException {
  public InvalidLoginException(final String message) {
    super(message);
  }
}
