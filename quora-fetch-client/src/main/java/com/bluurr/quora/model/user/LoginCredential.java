package com.bluurr.quora.model.user;

import lombok.*;

/**
 * Holds login credential information for logging into the Quora platform.
 */
@Builder
@Value
public class LoginCredential {
  private final String username;
  private final String password;
}
