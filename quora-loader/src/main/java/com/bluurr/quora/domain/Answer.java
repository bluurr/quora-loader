package com.bluurr.quora.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * Answer posted by a Quora user.
 */
@Builder
@Value
public class Answer {

  private final String answerBy;

  @Builder.Default
  private final List<String> paragraphs = List.of();
}
