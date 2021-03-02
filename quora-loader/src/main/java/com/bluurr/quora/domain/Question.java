package com.bluurr.quora.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * Question posted by a Quora user.
 */
@Builder
@Value
public class Question {
  private final String location;
  private final String title;
}
