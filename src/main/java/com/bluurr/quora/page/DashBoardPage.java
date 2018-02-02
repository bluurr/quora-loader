package com.bluurr.quora.page;

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
public class DashBoardPage extends PageObject<DashBoardPage>
{
	private static final int MAX_WAIT_SECONDS = 3;
	
	@FindBy(xpath="//textarea[contains(@class, 'selector_input') and @placeholder = 'Search Quora']")
	private WebElement searchBar;
	
	@FindBy(how=How.CLASS_NAME, className="lookup_bar_modal_overlay")
	private WebElement overlay;
	
	public SearchPage search(final String term)
	{
		searchBar.sendKeys(term);
		Bot.waitForElementToDisplay(overlay, MAX_WAIT_SECONDS);
		searchBar.sendKeys(Keys.ENTER);
		return new SearchPage().get();
	}
	
	@Override
	protected void isLoaded() throws Error 
	{
		Bot.waitForElementToDisplay(searchBar, MAX_WAIT_SECONDS);
	}
}