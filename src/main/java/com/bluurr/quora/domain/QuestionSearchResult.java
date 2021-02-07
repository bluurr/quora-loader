package com.bluurr.quora.domain;

import lombok.Builder;
import lombok.Value;

/**
 * A summary of a question posted by a Quora user.
 */
@Builder
@Value
public class QuestionSearchResult {
	private final String title;
	private final String location;
}
