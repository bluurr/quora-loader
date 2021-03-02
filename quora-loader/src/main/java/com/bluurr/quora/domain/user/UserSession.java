package com.bluurr.quora.domain.user;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserSession {
  private final String username;
}
