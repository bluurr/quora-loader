package com.bluurr.quora;

import java.net.URI;
import java.util.List;

import com.bluurr.quora.domain.LoginCredential;
import com.bluurr.quora.domain.Question;
import com.bluurr.quora.domain.QuestionSummary;
import com.bluurr.quora.driver.ChromeWebDriverFactory;
import com.bluurr.quora.driver.WebDriverFactory;

public class TestClass 
{
	public static void main(String[] args) 
	{
		final String username = args[0];
		final String password = args[1];
		
		LoginCredential login = new LoginCredential(username, password);

		WebDriverFactory factory = new ChromeWebDriverFactory();
		
		try(QuestionExtractor loader = new QuestionExtractor(login, URI.create("https://www.quora.com"), 
				factory))
		{
			List<QuestionSummary> results = loader.search("Java", 2);
			
			if(!results.isEmpty())
			{
				Question question = 
						loader.getQuestion(results.get(0).getLocation());
				
				int x = 0;
			}
		}
	}

}
