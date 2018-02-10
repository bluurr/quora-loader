package com.bluurr.quora.page.search;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.bluurr.quora.domain.QuestionSummary;
import com.github.webdriverextensions.WebComponent;

/**
 * Sub component of each question with Quora search page results.
 * 
 * @author Bluurr
 *
 */
public class SearchQuestionComponent extends WebComponent
{
	@FindAll({
		@FindBy(xpath = ".//*[@class='question_text']/span[@class='rendered_qtext']"),	
		@FindBy(xpath = ".//*[contains(@class, 'TopicNameSpan')]/span[@class='rendered_qtext']"),	
		@FindBy(xpath = ".//*[contains(@class, 'BoardItemTitle')]/span[@class='rendered_qtext']")
	})
	private WebElement question;
	
	@FindAll({
		@FindBy(className = "question_link"),	
		@FindBy(css = ".question_link.with_source"),	
		@FindBy(className = "TopicNameLink"),	
		@FindBy(className = "user"),	
		@FindBy(className = "BoardItemTitle")
	})
	private WebElement location;
	
	@FindBy(xpath=".//div[@class='answer_total']/span[@class='ans_count']")
	private List<WebElement> totalAnswers;
	
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
		summary.setAnswers(getAnswerTotal());
		return summary;
	}

	private int getAnswerTotal() 
	{
		if(!totalAnswers.isEmpty())
		{
			String answerCount = totalAnswers.get(0).getText();
			
			if(answerCount != null)
			{
				/** Example format:  Answer 1 of 1,229 */
				int last = answerCount.lastIndexOf("of");
				
				if(last != -1)
				{
					String totalAnswer = 
							answerCount.substring(last).replaceAll("[^0-9]", "");
					return Integer.parseInt(totalAnswer);
				}
			}
		}
		return 0;
	}
}