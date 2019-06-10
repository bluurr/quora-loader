package com.bluurr.quora.domain;

import com.google.common.base.Splitter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Answer posted by a Quora user.
 * 
 * @author Bluurr
 *
 */
@Data
public class Answer {
	private String username;
	private List<String> comments = new ArrayList<>();

	/**
	 * Gets all comments for the answer, any comments larger than the maxLength will be split.
	 * @param maxLength
	 * The max length of a single comment.
	 * @return
	 * A list of all comment, with no comment greater than the maxLength.
	 */
	public List<String> getSplitComments(final int maxLength) {
		final List<String> result = new ArrayList<>();
		
		for(String message : comments) {
			if(message.length() > maxLength) {
				result.addAll(Splitter.fixedLength(maxLength).splitToList(message));
			} else
			{
				result.add(message);
			}
		}

		return result;
	}
	

	public boolean hasAnswer() {
		return !getComments().isEmpty();
	}
}
