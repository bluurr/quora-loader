package com.bluurr.quora.page.question;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bluurr.quora.domain.Answer;
import com.github.webdriverextensions.WebComponent;

public class QuestionAnswerComponent extends WebComponent
{
	@FindBy(xpath=".//*[contains(@class, 'ui_qtext_para')] | .//*[contains(@class, 'ui_qtext_rendered_qtext')] ")
	private List<WebElement> messages;
	
	public QuestionAnswerComponent() 
	{
		super();
	}
	
	public Answer getAnswer()
	{
		List<String> results = 
				messages.stream().map(WebElement::getText).collect(Collectors.toList());
		
		Answer answer = new Answer();
		answer.setMessage(results);
		return answer;
	}
}
