package com.bluurr.quora.page.question;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bluurr.quora.domain.Answer;
import com.bluurr.quora.domain.Question;
import com.bluurr.quora.extension.BotExtra;
import com.bluurr.quora.page.PageObject;
import com.github.webdriverextensions.Bot;

/**
 * Question page for Quora once logged in.
 * 
 * @author chris
 *
 */
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

	public Question getQuestion(final int maxAnswers) 
	{
		Question question = new Question();
		question.setLocation(Bot.currentUrl());
		question.setAsked(questionTitle.getText());
		question.setAnswers(getAnswers(maxAnswers));
		return question;
	}
	
	private List<Answer> getAnswers(final int maxAnswers)
	{
		if(answers.size() < maxAnswers)
		{
			loadHiddenAnswers(maxAnswers);
		}

		List<Answer> results = 
				answers.stream().map(QuestionAnswerComponent::getAnswer).collect(Collectors.toList());
		return results;
	}
	
	private void loadHiddenAnswers(final int maxAnswers)
	{
		while(hasHiddenAnswers() && maxAnswers > answers.size())
		{
			int currentSize = answers.size();
			triggerAnswerLoad(currentSize);
			/**
			 * No more record were loaded.
			 */
			if(currentSize >= answers.size())
			{
				break;
			}
		}
	}

	private boolean hasHiddenAnswers()
	{
		return !Arrays.asList(loadingSpinner.getAttribute("class").split(" ")).contains("hidden");
	}
	
	private void triggerAnswerLoad(final int offset)
	{
		BotExtra.scrollToPageBottom();
		BotExtra.waitForNumberOfElementsToBeMoreThan(offset, answers);
	}
}