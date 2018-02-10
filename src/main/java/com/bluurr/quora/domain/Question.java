package com.bluurr.quora.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Question posted by a Quora user.
 * 
 * @author Bluurr
 *
 */
public class Question 
{
	private String location;
	private String asked;
	private List<Answer> answers;
	
	public Question()
	{
		super();
		this.answers = new ArrayList<>();
	}
	
	public String getLocation() 
	{
		return location;
	}
	
	public void setLocation(final String location)
	{
		this.location = location;
	}
	
	public List<Answer> getAnswers() 
	{
		return answers;
	}
	
	public void setAnswers(final List<Answer> answers) 
	{
		this.answers = answers;
	}

	public String getAsked() 
	{
		return asked;
	}

	public void setAsked(final String asked)
	{
		this.asked = asked;
	}

	@Override
	public String toString() 
	{
		return "Question [location=" + location + ", asked=" + asked + ", answers=" + answers + "]";
	}
}
