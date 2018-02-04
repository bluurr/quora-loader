package com.bluurr.quora.page.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bluurr.quora.domain.Answer;
import com.bluurr.quora.domain.Question;
import com.bluurr.quora.extension.BotExtra;
import com.bluurr.quora.page.PageObject;
import com.github.webdriverextensions.Bot;

public class QuestionPage extends PageObject
{
	public static QuestionPage open(final String location)
	{
		QuestionPage page = new QuestionPage();
		Bot.driver().navigate().to(location);
		return page;
	}
	
	@FindBy(xpath="//*[@class='QuestionArea']//span[@class='rendered_qtext']")
	private WebElement questionTitle;

	@FindBy(xpath="//div[@class='AnswerListDiv']//div[@class='pagedlist_item']")
	private List<QuestionAnswerComponent> answers;
	
	@FindBy(className="spinner_display_area")
	private WebElement loadingSpinner;

	public Question fetchWithComment(final int maxComments) 
	{
		Question question = new Question();
		question.setLocation(Bot.currentUrl());
		question.setAsked(questionTitle.getText());
		question.setAnswers(getAnswers(maxComments));
		return question;
	}
	
	private List<Answer> getAnswers(final int maxComments)
	{
		int offset = 0;
		List<Answer> results = new ArrayList<>();

		do
		{
			for(; offset < answers.size(); offset++)
			{
				Optional<Answer> answer = 
						collect(answers.get(offset));
				
				if(answer.isPresent())
				{
					results.add(answer.get());
					
					/**
					 * Prevent any more fetching as we are at the requested cap
					 * Saves some cycles depending on how large the fetch buffer is.
					 */
					if(results.size() >= maxComments)
					{
						offset = maxComments;
						break;
					}
				}
			}
		} while(fetchNext(offset, maxComments));

		return results;
	}
	
	private boolean fetchNext(final int offset, final int max) 
	{
		if(offset >= max || !hasMore())
		{
			return false;
		}
		
		fetchNextBlock(offset);
		
		boolean hasMore = answers.size() != offset;
		return hasMore;
	}
	
	private boolean hasMore()
	{
		return !Arrays.asList(loadingSpinner.getAttribute("class").split(" ")).contains("hidden");
	}
	
	private void fetchNextBlock(final int offset)
	{
		BotExtra.scrollToPageBottom();
		BotExtra.waitForNumberOfElementsToBeMoreThan(offset, answers);
	}
	
	private Optional<Answer> collect(final QuestionAnswerComponent answer)
	{
		Bot.scrollTo(answer);
		boolean valid = !answer.getAnswer().getComments().isEmpty();

		return valid ? Optional.of(answer.getAnswer()) : Optional.empty();
	}
}