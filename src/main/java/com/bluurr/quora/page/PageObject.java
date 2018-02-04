package com.bluurr.quora.page;

import org.openqa.selenium.support.PageFactory;

import com.github.webdriverextensions.Bot;
import com.github.webdriverextensions.WebDriverExtensionFieldDecorator;

/**
 * Base for Selenium page object instances.
 * 
 * @author chris
 *
 */
public abstract class PageObject
{
	public PageObject()
	{ 
		PageFactory.initElements(new WebDriverExtensionFieldDecorator(Bot.driver()), this); 
	}
	
	protected void waitForLoaded()
	{
	}
}
