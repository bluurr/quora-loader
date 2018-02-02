package com.bluurr.quora.extension;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.webdriverextensions.Bot;

/**
 * 
 * @author chris
 *
 */
public class BotExtra 
{
	private static final int DEFAULT_TIMEOUT_SECONDS = 3;
	
	public static void scrollToPageBottom()
	{
		getJavascriptDriver().executeScript("window.scrollBy(0, document.body.scrollHeight)", "");
	}
	
	private static JavascriptExecutor getJavascriptDriver()
	{
		return ((JavascriptExecutor) Bot.driver());
	}

	public static void waitForNumberOfElementsToBeMoreThan(final int startSize, 
			final List<? extends WebElement> elements) 
	{
		WebDriverWait wait = new WebDriverWait(Bot.driver(), DEFAULT_TIMEOUT_SECONDS, 1000);
	    wait.until(driver -> elements.size() > startSize);
	}
}
