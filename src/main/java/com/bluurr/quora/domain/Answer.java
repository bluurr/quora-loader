package com.bluurr.quora.domain;

import java.util.List;

public class Answer 
{
	private String who;
	private List<String> messages;
	
	public String getWho() 
	{
		return who;
	}
	
	public void setWho(final String who) 
	{
		this.who = who;
	}
	
	public List<String> getMessages()
	{
		return messages;
	}
	
	public void setMessage(final List<String> messages) 
	{
		this.messages = messages;
	}

	@Override
	public String toString() 
	{
		return "Answer [who=" + who + ", message=" + messages + "]";
	}
}
