package com.bluurr.quora.it;

import java.net.URI;
import java.util.List;

import org.openqa.selenium.chrome.ChromeDriver;

import com.bluurr.quora.domain.LoginCredential;
import com.bluurr.quora.domain.Question;
import com.bluurr.quora.domain.QuestionSummary;
import com.bluurr.quora.extension.BotExtra;
import com.bluurr.quora.page.DashBoardPage;
import com.bluurr.quora.page.LoginPage;
import com.bluurr.quora.page.question.QuestionPage;
import com.github.webdriverextensions.Bot;
import com.github.webdriverextensions.WebDriverExtensionsContext;

public class TestClass 
{
	public static void main(String[] args) 
	{
		init();
		
		try
		{
			DashBoardPage dashboard = 
					LoginPage.open(getTarget(args)).login(getLogin(args));
			
			List<QuestionSummary> results = 
					dashboard.search("aws").getQuestions(5);
			
			if(!results.isEmpty())
			{
				final QuestionSummary top = results.get(0);
				
				QuestionPage questionPage = 
						QuestionPage.open(top.getLocation());
				
				Question question = questionPage.getQuestion(10);
				
				int x = 0;
			}
		} finally
		{
			BotExtra.closeDriver();
		}
	}
	
	private static void init()
	{
		WebDriverExtensionsContext.setDriver(new ChromeDriver());
		Bot.driver().manage().deleteAllCookies();
	}
	
	private static LoginCredential getLogin(String[] args)
	{
		final String username = args[0];
		final String password = args[1];
		
		return new LoginCredential(username, password);
	}
	
	private static URI getTarget(String[] args)
	{
		return URI.create("https://www.quora.com");
	}
}