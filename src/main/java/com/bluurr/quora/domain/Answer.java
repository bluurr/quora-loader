package com.bluurr.quora.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Splitter;

/**
 * Answer posted by a Quora user.
 * 
 * @author Bluurr
 *
 */
public class Answer 
{
	private String username;
	private List<String> comments;
	
	public Answer()
	{
		super();
		this.comments = new ArrayList<>();
	}
	
	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(final String username) 
	{
		this.username = username;
	}
	
	public List<String> getComments()
	{
		return comments;
	}
	
	public void setComments(final List<String> comments) 
	{
		this.comments = comments;
	}
	
	/**
	 * Gets all comments for the answer, any comments larger than the maxLength will be split.
	 * @param maxLength
	 * The max length of a single comment.
	 * @return
	 * A list of all comment, with no comment greater than the maxLength.
	 */
	public List<String> getSplitComments(final int maxLength)
	{
		final List<String> result = new ArrayList<>();
		
		for(String message : comments)
		{
			if(message.length() > maxLength)
			{
				result.addAll(Splitter.fixedLength(maxLength).splitToList(message));
			} else
			{
				result.add(message);
			}
		}

		return result;
	}
	

	public boolean hasAnswer() 
	{
		return !getComments().isEmpty();
	}
	
	@Override
	public String toString() 
	{
		return "Answer [username=" + username + ", comments=" + comments + "]";
	}
}
