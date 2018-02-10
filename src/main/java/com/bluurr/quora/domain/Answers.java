package com.bluurr.quora.domain;

/**
 * 
 * @author Bluurr
 *
 */
public class Answers 
{
	public static Answers limit(final int limit)
	{
		Answers answers = new Answers();
		answers.setLimit(limit);
		return answers;
	}

	private int limit;

	public int getLimit() 
	{
		return limit;
	}

	public void setLimit(final int limit) 
	{
		this.limit = limit;
	}

	@Override
	public String toString() 
	{
		return "Answers [limit=" + limit + "]";
	}
}
