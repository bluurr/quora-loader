package com.bluurr.quora.driver;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * 
 * @author chris
 *
 */
public class ExtendedExpectedCondition 
{
   /**
    * An expectation for checking number of WebElements with given locator
    *
    * @param locator used to find the element
    * @param number  user to define exact number of elements
    * @return Boolean true when size of elements list is equal to defined
    */
	public static ExpectedCondition<Boolean> numberOfElementsToBeMoreThan(final List<WebElement> element, 
			final int amount) 
	{
		return new ExpectedCondition<Boolean>() 
		{
			@Override
			public Boolean apply(WebDriver driver) 
			{
				return element.size() > amount;
			}
		};
	}

}
