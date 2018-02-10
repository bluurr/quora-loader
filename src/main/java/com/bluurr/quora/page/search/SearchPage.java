package com.bluurr.quora.page.search;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.StringUtils;
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

/**
 * Question search page for Quora once logged in.
 * 
 * @author Bluurr
 *
 */
public class SearchPage extends PageObject
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchPage.class);
	
	public static SearchPage openDirect(final String term, final String type)
	{
		if(StringUtils.isBlank(term))
		{
			throw new IllegalArgumentException("Term must be non-blank.");
		}
		
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
		search.waitForLoaded();
		return search;
	}
	
	@FindBy(className="pagedlist_item")
	private List<SearchQuestionComponent> questions;
	
	@FindBy(className="pagedlist_hidden")
	private List<WebElement> hiddenQuestions;
	
	@FindBy(className="results_empty")
	private List<WebElement> noResults;

	public int getQuestionSize()
	{
		return questions.size();
	}
		
	public List<QuestionSummary> getQuestions(final int limit)
	{
		if(questions.size() < limit)
		{
			loadHiddenQuestions(limit);
		}
		
		List<QuestionSummary> results = 
				questions.stream().map(SearchQuestionComponent::getSummary).collect(Collectors.toList());
		return results;
	}
	
	@Override
	protected void waitForLoaded()
	{
		if(noResults.isEmpty())
		{
			BotExtra.waitForNumberOfElementsToBeMoreThan(0, questions);
			
			try
			{
				BotExtra.waitForNumberOfElementsToBeMoreThan(0, hiddenQuestions);
			} catch(final TimeoutException err)
			{
				LOGGER.warn("Page didn't load any extra results.", err);
			}
		}
	}
	
	private boolean hasHiddenQuestions()
	{
		return !hiddenQuestions.isEmpty();
	}
	
	private void loadHiddenQuestions(final int maxQuestions) 
	{
		if(maxQuestions < 1 )
		{
			throw new IllegalArgumentException("Max questions count must be greater than 1.");
		}
		
		while(hasHiddenQuestions() && maxQuestions > getQuestionSize())
		{
			int current = getQuestionSize();
			triggerHiddenQuestionsLoad(current);
			
			/**
			 * No more record were loaded.
			 */
			if(current >= getQuestionSize())
			{
				break;
			}
		}
	}
	
	private void triggerHiddenQuestionsLoad(final int offset)
	{
		BotExtra.scrollToPageBottom();
		BotExtra.waitForNumberOfElementsToBeMoreThan(offset, questions);
	}
}