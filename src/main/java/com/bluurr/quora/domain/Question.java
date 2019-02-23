package com.bluurr.quora.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Question posted by a Quora user.
 * 
 * @author Bluurr
 *
 */
@Data
public class Question {
	private String location;
	private String asked;
	private List<Answer> answers = new ArrayList<>();
	private List<RelatedQuestion> related = new ArrayList<>();
}
