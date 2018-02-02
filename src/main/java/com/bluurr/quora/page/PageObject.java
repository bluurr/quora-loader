package com.bluurr.quora.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.github.webdriverextensions.Bot;
import com.github.webdriverextensions.WebDriverExtensionFieldDecorator;

/**
 * Base for Selenium page object instances.
 * 
 * @author chris
 *
 */
public abstract class PageObject<T extends LoadableComponent<T>> extends LoadableComponent<T>
{
	public PageObject()
	{ 
		PageFactory.initElements(new WebDriverExtensionFieldDecorator(Bot.driver()), this); 
	}
	
	@Override
	protected void load() 
	{
	}
	
	@Override
	protected void isLoaded() throws Error 
	{
	}
	
	protected WebDriver getDriver()
	{
		return Bot.driver();
	}
	
	protected JavascriptExecutor getJavascriptDriver()
	{
		return ((JavascriptExecutor) getDriver());
	}
}
