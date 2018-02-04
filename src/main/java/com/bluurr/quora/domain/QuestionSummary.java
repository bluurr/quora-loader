package com.bluurr.quora.domain;

/**
 * A summary of a question posted by a Quroa user.
 * 
 * @author chris
 *
 */
public class QuestionSummary 
{
	private String id;
	private String location;
	private String question;
	private int answers;

	public String getId() 
	{
		return id;
	}
	
	public void setId(final String id) 
	{
		this.id = id;
	}
	
	public String getLocation() 
	{
		return location;
	}
	
	public void setLocation(final String location) 
	{
		this.location = location;
	}
	
	public String getQuestion() 
	{
		return question;
	}
	
	public void setQuestion(final String question) 
	{
		this.question = question;
	}
	
	public int getAnswers() 
	{
		return answers;
	}
	
	public void setAnswers(final int answers) 
	{
		this.answers = answers;
	}

	@Override
	public String toString() 
	{
		return "QuestionSummary [id=" + id + ", location=" + location + ", question=" + question + ", answers="
				+ answers + "]";
	}
}