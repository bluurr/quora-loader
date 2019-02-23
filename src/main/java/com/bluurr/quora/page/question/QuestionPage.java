package com.bluurr.quora.page.question;

import com.bluurr.quora.domain.Answer;
import com.bluurr.quora.domain.Answers;
import com.bluurr.quora.domain.Question;
import com.bluurr.quora.domain.RelatedQuestion;
import com.bluurr.quora.extension.BotExtra;
import com.bluurr.quora.page.PageObject;
import com.github.webdriverextensions.Bot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Question page for Quora once logged in.
 * 
 * @author Bluurr
 *
 */
public class QuestionPage extends PageObject {
	public static QuestionPage open(final String location) {
		QuestionPage page = new QuestionPage();
		Bot.driver().navigate().to(location);
		return page;
	}
	
	@FindAll({
		@FindBy(xpath="//*[contains(@class, 'QuestionArea')]//span[@class='rendered_qtext']"),
		@FindBy(xpath="//*[contains(@class, 'QuestionArea')]//span[@class='ui_qtext_rendered_qtext']")
	})
	private WebElement questionTitle;

	@FindBy(xpath="//div[@class='paged_list_wrapper']//div[@class='pagedlist_item']")
	private List<QuestionAnswerComponent> answers;

	@FindBy(xpath="//div[contains(@class, 'QuestionMain')]//*[@class='question_link']")
	private List<WebElement> related;
	
	@FindBy(className="spinner_display_area")
	private WebElement loadingSpinner;

	public Question getQuestion(final Answers answers) {
		Question question = new Question();
		question.setLocation(Bot.currentUrl());
		question.setAsked(questionTitle.getText());
		question.setAnswers(getAnswers(answers.getLimit()));
		question.setRelated(getRelated());
		return question;
	}

	private List<Answer> getAnswers(final int maxAnswers) {
		if(answers.size() < maxAnswers) {
			loadHiddenAnswers(maxAnswers);
		}

		List<Answer> results = 
				answers.stream().map(QuestionAnswerComponent::getAnswer).filter(Answer::hasAnswer).collect(Collectors.toList());
		return results;
	}
	
	private List<RelatedQuestion> getRelated() {
		List<RelatedQuestion> result = new ArrayList<>(related.size());
		
		for(WebElement element : related) {
			String location = element.getAttribute("href");
			WebElement name = element.findElement(By.className("ui_qtext_rendered_qtext"));
			
			if(name != null) {
				RelatedQuestion question = new RelatedQuestion();
				question.setLocation(location);
				question.setQuestion(name.getText());
				result.add(question);
			}
		}

		return result;
	}

	private void loadHiddenAnswers(final int maxAnswers) {
		while(hasHiddenAnswers() && maxAnswers > answers.size()) {
			int currentSize = answers.size();
			triggerAnswerLoad(currentSize);
			/*
			 * No more record were loaded.
			 */
			if(currentSize >= answers.size()) {
				break;
			}
		}
	}

	private boolean hasHiddenAnswers() {
		return !Bot.hasClass("hidden", loadingSpinner);
	}
	
	private void triggerAnswerLoad(final int offset) {
		BotExtra.scrollToPageBottom();
		BotExtra.waitForNumberOfElementsToBeMoreThan(offset, answers);
	}
}