package com.bluurr.quora.page.search;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bluurr.quora.domain.QuestionSummary;
import com.bluurr.quora.extension.BotExtra;
import com.bluurr.quora.page.PageObject;
import com.github.webdriverextensions.Bot;

public class SearchPage extends PageObject
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchPage.class);
	
	public static SearchPage openDirect(final String term, final String type)
	{
		WebDriver driver = Bot.driver();
		try
		{
			String encodedTerm = new URLCodec().encode(term);
			driver.navigate().to(driver.getCurrentUrl() + "/search?q="+encodedTerm+"&type="+type);
		} catch(final EncoderException err)
		{
			throw new IllegalArgumentException("Unable to encode term.", err);
		}
		return open();
	}
	
	public static SearchPage open() 
	{
		SearchPage search = new SearchPage();
		search.waitTillLoaded();
		return search;
	}
	
	@FindBy(className="pagedlist_item")
	private List<SearchQuestionComponent> questions;
	
	@FindBy(className="pagedlist_hidden")
	private List<WebElement> unloadedQuestions;
	
	@FindBy(className="results_empty")
	private List<WebElement> noResult;

	public int getQuestionSize()
	{
		return questions.size();
	}
		
	public List<QuestionSummary> fetch(final int maxQuestions)
	{
		buffer(maxQuestions);
		List<QuestionSummary> results = 
				questions.stream().map(SearchQuestionComponent::getSummary).collect(Collectors.toList());
		return results;
	}
	
	private boolean hasUnloadedQuestions()
	{
		return !unloadedQuestions.isEmpty();
	}
	
	private void buffer(int itemCount) 
	{
		if(itemCount < 1 )
		{
			throw new IllegalArgumentException("Itemcount count must be greater than 1.");
		}
		
		while(hasUnloadedQuestions() && itemCount > getQuestionSize())
		{
			int current = getQuestionSize();
			fetchNext(current);
			
			/**
			 * No more record were loaded.
			 */
			if(current >= getQuestionSize())
			{
				break;
			}
		}
	}
	
	private void fetchNext(final int offset)
	{
		BotExtra.scrollToPageBottom();
		BotExtra.waitForNumberOfElementsToBeMoreThan(offset, questions);
	}
		
	private void waitTillLoaded()
	{
		if(noResult.isEmpty())
		{
			BotExtra.waitForNumberOfElementsToBeMoreThan(0, questions);
			
			try
			{
				BotExtra.waitForNumberOfElementsToBeMoreThan(0, unloadedQuestions);
			} catch(final TimeoutException err)
			{
				LOGGER.warn("Page didn't load any extra results.", err);
			}
		}
	}
}