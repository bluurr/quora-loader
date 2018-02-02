package com.bluurr.quora.page.question;

import java.util.List;

import org.openqa.selenium.support.FindBy;

import com.bluurr.quora.domain.Question;
import com.bluurr.quora.extension.BotExtra;
import com.bluurr.quora.page.PageObject;
import com.github.webdriverextensions.Bot;

public class QuestionPage extends PageObject<QuestionPage>
{
	private static final int MAX_FETCH = 25;
	
	@FindBy(xpath="//div[@class='AnswerListDiv']//div[@class='pagedlist_item']")
	private List<QuestionAnswerComponent> answers;
		
	public void openQuestion(final String location) 
	{
		getDriver().navigate().to(location);
	}
	
	public Question getQuestion()
	{
		Question question = new Question();
		
		int offset = 0;
		
		do
		{
			for(; offset < answers.size(); offset++)
			{
				collect(question, answers.get(offset));
			}
		} while(fetchNext(offset));
		
		return question;
	}
	
	private boolean fetchNext(final int offset) 
	{
		if(offset >= MAX_FETCH)
		{
			return false;
		}
		
		fetchNextBlock(offset);
		
		boolean hasMore = answers.size() != offset;
		return hasMore;
	}
	
	private void fetchNextBlock(final int offset)
	{
		BotExtra.scrollToPageBottom();
		BotExtra.waitForNumberOfElementsToBeMoreThan(offset, answers);
	}
	
	private void collect(final Question question, final QuestionAnswerComponent answer)
	{
		Bot.scrollTo(answer);
		question.getAnswers().add(answer.getAnswer());
	}
}
