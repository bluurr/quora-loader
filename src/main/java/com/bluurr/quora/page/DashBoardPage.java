package com.bluurr.quora.page;

import com.bluurr.quora.page.search.SearchPage;
import com.github.webdriverextensions.Bot;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Objects;

/**
 * Main DashBoard page for Quora once logged in.
 * 
 * @author Bluurr
 *
 */
public class DashBoardPage extends PageObject {
	public static DashBoardPage open() {
		DashBoardPage dashboard = new DashBoardPage();
		dashboard.waitForLoaded();
		return dashboard;
	}
	
	private static final int MAX_WAIT_SECONDS = 10;
	
	@FindBy(xpath="//*[contains(@class, 'selector_input') and @placeholder = 'Search Quora']")
	private WebElement searchBar;
	
	@FindBy(className="lookup_bar_modal_overlay")
	private WebElement overlay;
	
	public SearchPage search(final String term) {
		searchPrecondition(term);
		
		searchBar.sendKeys(term);
		/** Pop-up overlay ensures the search has fully loaded otherwise searching will fail to submit. */
		Bot.waitForElementToDisplay(overlay, MAX_WAIT_SECONDS);
		searchBar.sendKeys(Keys.ENTER);
		return SearchPage.open();
	}
	
	@Override
	protected void waitForLoaded() {
		Bot.waitForElementToDisplay(searchBar, MAX_WAIT_SECONDS);
	}
	
	private void searchPrecondition(final String term) {
		Objects.requireNonNull(term, "Search term must not be null.");
		
		if(term.length() < 2) {
			throw new IllegalArgumentException("Search term must be at least 2 characters in length.");
		}
	}
}