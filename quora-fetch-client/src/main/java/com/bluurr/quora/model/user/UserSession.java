package com.bluurr.quora.model.user;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserSession {
  private final String username;
}
