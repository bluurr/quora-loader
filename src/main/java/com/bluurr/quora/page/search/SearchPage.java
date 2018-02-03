package com.bluurr.quora.page.search;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bluurr.quora.domain.QuestionSummary;
import com.bluurr.quora.extension.BotExtra;
import com.bluurr.quora.page.PageObject;

public class SearchPage extends PageObject
{
	public static SearchPage open() 
	{
		SearchPage search = new SearchPage();
		search.waitTillLoaded();
		return search;
	}
	
	@FindBy(xpath="//div[@class='pagedlist_item']")
	private List<SearchQuestionComponent> questions;
	
	@FindBy(xpath="//div[contains(@class, 'pagedlist_hidden')]")
	private List<WebElement> firstUnloadQuestion;

	public boolean hasNext()
	{
		return !firstUnloadQuestion.isEmpty();
	}

	public int getLoadedQuestionSize()
	{
		return questions.size();
	}
	
	public SearchPage fetch(int maxItemCount) 
	{
		if(maxItemCount < 1 || maxItemCount > 50)
		{
			throw new IllegalArgumentException("Itemcount count must be between 1 and 50 in size.");
		}
		
		while(hasNext() && maxItemCount > getLoadedQuestionSize())
		{
			int current = getLoadedQuestionSize();
			fetchNext(current);
			
			/**
			 * No more record were loaded.
			 */
			if(current <= getLoadedQuestionSize())
			{
				break;
			}
		}
		
		return this;
	}
	
	public List<QuestionSummary> getSummary()
	{
		List<QuestionSummary> results = 
				questions.stream().map(SearchQuestionComponent::getSummary).collect(Collectors.toList());
		return results;
	}
	
	private void fetchNext(final int offset)
	{
		BotExtra.scrollToPageBottom();
		BotExtra.waitForNumberOfElementsToBeMoreThan(offset, questions);
	}
		
	private void waitTillLoaded()
	{
		BotExtra.waitForNumberOfElementsToBeMoreThan(0, firstUnloadQuestion);
	}
}