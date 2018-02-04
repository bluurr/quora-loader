package com.bluurr.quora.page.question;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bluurr.quora.domain.Answer;
import com.github.webdriverextensions.WebComponent;

/**
 * Sub component of each answer with Quora question page.
 * 
 * @author chris
 *
 */
public class QuestionAnswerComponent extends WebComponent
{
	@FindBy(className="ui_qtext_para")
	private List<WebElement> messages;
	
	@FindBy(className="ui_qtext_expanded")
	private List<WebElement> extendedMessages;
	
	@FindBy(xpath=".//a[@class='user']")
	private List<WebElement> user;
	
	public QuestionAnswerComponent() 
	{
		super();
	}
	
	public Answer getAnswer()
	{
		List<String> results = toMessagesText(messages);

		if(results.isEmpty())
		{
			results = toMessagesText(extendedMessages);
		}
		
		Answer answer = new Answer();
		answer.setComments(results);
		answer.setUsername(getUsername());
		return answer;
	}
	
	private @Nullable String getUsername()
	{
		return !user.isEmpty() ? user.get(0).getText() : null;
	}
	
	private List<String> toMessagesText(final List<WebElement> messages)
	{
		return messages.stream().map(WebElement::getText).collect(Collectors.toList());
	}
}