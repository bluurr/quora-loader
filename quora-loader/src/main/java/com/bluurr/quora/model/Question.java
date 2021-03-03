package com.bluurr.quora.model;

import lombok.Builder;
import lombok.Value;

/**
 * Question posted by a Quora user.
 */
@Builder
@Value
public class Question {
  private final String location;
  private final String title;
}
