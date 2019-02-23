package com.bluurr.quora.domain;

import lombok.Data;

/**
 * A summary of a question posted by a Quora user.
 * 
 * @author Bluurr
 *
 */
@Data
public class QuestionSummary {
	private String id;
	private String location;
	private String question;
	private int answers;
}