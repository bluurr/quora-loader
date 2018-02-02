package com.bluurr.quora.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 
 * @author chris
 *
 */
public class ChromeWebDriverFactory implements WebDriverFactory
{
	public ChromeWebDriverFactory()
	{
		super();
	}

	@Override
	public WebDriver get() 
	{
		return new ChromeDriver();
	}
}
