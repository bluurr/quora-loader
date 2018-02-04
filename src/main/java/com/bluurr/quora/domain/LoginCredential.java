package com.bluurr.quora.domain;

/**
 * Holds login credential information for logging into the Quroa platform.
 * 
 * @author chris
 *
 */
public class LoginCredential
{
	private String username;
	private String password;
	
	public LoginCredential()
	{
		super();
	}
	
	public LoginCredential(final String username, final String password)
	{
		this();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(final String username) 
	{
		this.username = username;
	}
	
	public String getPassword() 
	{
		return password;
	}
	
	public void setPassword(final String password) 
	{
		this.password = password;
	}

	@Override
	public String toString() 
	{
		return "LoginCredential [username=" + username + ", password=" + password == null ? null : "******" + "]";
	}
}