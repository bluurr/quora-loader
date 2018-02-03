package com.bluurr.quora.page;

import java.util.Objects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.bluurr.quora.page.search.SearchPage;
import com.github.webdriverextensions.Bot;

/**
 * 
 * @author chris
 *
 */
public class DashBoardPage extends PageObject
{
	public static DashBoardPage open() 
	{
		DashBoardPage dashboard = new DashBoardPage();
		dashboard.waitTillLoaded();
		return dashboard;
	}
	
	private static final int MAX_WAIT_SECONDS = 3;
	
	@FindBy(xpath="//textarea[contains(@class, 'selector_input') and @placeholder = 'Search Quora']")
	private WebElement searchBar;
	
	@FindBy(how=How.CLASS_NAME, className="lookup_bar_modal_overlay")
	private WebElement overlay;
	
	public SearchPage search(final String term)
	{
		searchPrecondition(term);
		
		searchBar.sendKeys(term);
		Bot.waitForElementToDisplay(overlay, MAX_WAIT_SECONDS);
		searchBar.sendKeys(Keys.ENTER);
		return SearchPage.open();
	}
	
	private void waitTillLoaded()
	{
		Bot.waitForElementToDisplay(searchBar, MAX_WAIT_SECONDS);
	}
	
	private void searchPrecondition(final String term)
	{
		Objects.requireNonNull(term, "Search term must not be null.");
		
		if(term.length() < 2)
		{
			throw new IllegalArgumentException("Search term must be at least 2 characters in length.");
		}
	}
}