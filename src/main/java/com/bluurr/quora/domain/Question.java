package com.bluurr.quora.domain;

import java.util.ArrayList;
import java.util.List;

public class Question 
{
	private String location;
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

	@Override
	public String toString() 
	{
		return "Question [location=" + location + ", answers=" + answers + "]";
	}
}
