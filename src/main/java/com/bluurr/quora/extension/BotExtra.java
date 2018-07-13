package com.bluurr.quora.extension;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.webdriverextensions.Bot;
import com.github.webdriverextensions.WebDriverExtensionsContext;

/**
 * Extra Web driver helper methods following on from @see {com.github.webdriverextensions.Bot}.
 * 
 * @author Bluurr
 *
 */
public class BotExtra 
{
	private static int getDefaultTimeout() {
		String value = System.getenv("DEFAULT_BOT_TIMEOUT");
		if(value != null && !value.isEmpty()) {
			return Integer.valueOf(value);
		}
		/** Default is four seconds */
		return 4;
	}
	
	private static final int DEFAULT_TIMEOUT_SECONDS = getDefaultTimeout();
	private static final int DEFAULT_WAIT_MILLISECOND = 250;
	
    public static void setDriver(final WebDriver driver) 
    {
    	WebDriverExtensionsContext.setDriver(driver);
    }
	
	public static void closeDriver() 
	{
		Bot.driver().close();
		WebDriverExtensionsContext.removeDriver();
	}

	public static void scrollToPageBottom()
	{
		Bot.executeJavascript("window.scrollBy(0, document.body.scrollHeight)", "");
	}
	
	public static void waitForNumberOfElementsToBeMoreThan(final int startSize, 
			final List<? extends WebElement> elements) 
	{
		WebDriverWait wait = new WebDriverWait(Bot.driver(), DEFAULT_TIMEOUT_SECONDS, DEFAULT_WAIT_MILLISECOND);
	    wait.until(driver -> elements.size() > startSize);
	}
	
	/**
	 * Wait until any single element inside any list is displayed.
	 * 
	 * @param elements
	 */
	@SafeVarargs
	public static void waitForOneDisplay(final List<? extends WebElement>... elements)
	{
		WebDriverWait wait = new WebDriverWait(Bot.driver(), DEFAULT_TIMEOUT_SECONDS, DEFAULT_WAIT_MILLISECOND);
	    wait.until(driver -> isAtLeastOneDisplay(elements));
	}
	
	public static void waitForOneDisplay(final List<? extends WebElement> elements)
	{
		WebDriverWait wait = new WebDriverWait(Bot.driver(), DEFAULT_TIMEOUT_SECONDS, DEFAULT_WAIT_MILLISECOND);
	    wait.until(driver -> isAtLeastOneDisplay(elements));
	}
	
	@SafeVarargs
	public static boolean isAtLeastOneDisplay(final List<? extends WebElement>... elements)
	{
		for(List<? extends WebElement> element : elements)
		{
    		if(isAtLeastOneDisplay(element))
    		{
    			return true;
    		}
    	}
		
		return false;
	}
	
	/**
	 * Check if any single element inside any list is displayed.
	 * 
	 * @param elements
	 */
	public static boolean isAtLeastOneDisplay(final List<? extends WebElement> elements)
	{
		for(WebElement element : elements)
    	{
    		if(Bot.isDisplayed(element))
    		{
    			return true;
    		}
    	}
		return false;
	}
}