package com.bluurr.quora.page.search;

import javax.annotation.Nullable;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bluurr.quora.domain.QuestionSummary;
import com.github.webdriverextensions.WebComponent;

public class SearchQuestionComponent extends WebComponent
{
	@FindBy(xpath=".//span[@class='question_text']/span[@class='rendered_qtext']")
	private WebElement question;

	@FindBy(xpath=".//a[@class='question_link' or @class='question_link with_source']")
	private WebElement location;
	
	@FindBy(xpath=".//div[@class='answer_total']/span[@class='ans_count']")
	private WebElement totalAnswers;
	
	public SearchQuestionComponent()
	{
		super();
	}
	
	public QuestionSummary getSummary()
	{
		QuestionSummary summary = new QuestionSummary();
		
		summary.setId(getAttribute("id"));
		summary.setQuestion(question.getText());
		summary.setLocation(location.getAttribute("href"));
		summary.setAnswers(toTotal());
		return summary;
	}

	private @Nullable Integer toTotal() 
	{
		String totalValue = totalAnswers.getText();
		
		if(totalValue != null)
		{
			int last = totalValue.lastIndexOf("of");
			
			if(last != -1)
			{
				return Integer.parseInt(totalValue.substring(last + 2, totalValue.length() - 1).trim());
			}
		}
		return null;
	}
}