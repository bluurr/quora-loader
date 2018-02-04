package com.bluurr.quora.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Splitter;

public class Answer 
{
	private String who;
	private List<String> comments;
	
	public Answer()
	{
		super();
		this.comments = new ArrayList<>();
	}
	
	public String getWho() 
	{
		return who;
	}
	
	public void setWho(final String who) 
	{
		this.who = who;
	}
	
	public List<String> getComments()
	{
		return comments;
	}
	
	public void setMessage(final List<String> comments) 
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
	public List<String> getComments(final int maxLength)
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

	@Override
	public String toString() 
	{
		return "Answer [who=" + who + ", comments=" + comments + "]";
	}
}
