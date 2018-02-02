package com.bluurr.quora.page.search;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bluurr.quora.domain.QuestionSummary;
import com.bluurr.quora.driver.ExtendedExpectedCondition;
import com.bluurr.quora.page.PageObject;

public class SearchPage extends PageObject<SearchPage>
{
	@FindBy(xpath="//div[@class='pagedlist_item']")
	private List<SearchQuestionComponent> questions;
	
	@FindBy(xpath="//div[contains(@class, 'pagedlist_hidden')]")
	private List<WebElement> firstUnloadQuestion;

	public void search(final String term) 
	{
		//getDriver().navigate().to(baseLocation.toASCIIString() + "/search?q=" + term);
	}
	
	public boolean hasPending()
	{
		return !firstUnloadQuestion.isEmpty();
	}
	
	public String getTopPendingQuestionId()
	{
		return firstUnloadQuestion.isEmpty() ? "" : firstUnloadQuestion.get(0).getAttribute("id");
	}
	
	public List<QuestionSummary> getSummary()
	{
		List<QuestionSummary> results = 
				questions.stream().map(SearchQuestionComponent::getSummary).collect(Collectors.toList());
		return results;
	}
	
	@Override
	protected void isLoaded() throws Error 
	{
		WebDriverWait wait = new WebDriverWait(getDriver(), 3);
		wait.until(ExtendedExpectedCondition.numberOfElementsToBeMoreThan(firstUnloadQuestion, 0));
	}
}