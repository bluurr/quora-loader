package com.bluurr.quora.domain;

/**
 * 
 * @author Bluurr
 *
 */
public class RelatedQuestion 
{
	private String question;
	private String location;
	
	public String getQuestion()
	{
		return question;
	}
	
	public void setQuestion(final String question) 
	{
		this.question = question;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public void setLocation(final String location) 
	{
		this.location = location;
	}

	@Override
	public String toString() 
	{
		return "RelatedQuestion [question=" + question + ", location=" + location + "]";
	}
}