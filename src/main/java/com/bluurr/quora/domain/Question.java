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
	private List<RelatedQuestion> related;
	
	public Question()
	{
		super();
		this.answers = new ArrayList<>();
		this.related = new ArrayList<>();
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

	public List<RelatedQuestion> getRelated() 
	{
		return related;
	}

	public void setRelated(final List<RelatedQuestion> related) 
	{
		this.related = related;
	}

	@Override
	public String toString() 
	{
		return "Question [location=" + location + ", asked=" + asked + ", answers=" + answers + ", related=" + related
				+ "]";
	}
}
