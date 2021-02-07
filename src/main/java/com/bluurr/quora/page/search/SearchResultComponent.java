package com.bluurr.quora.page.search;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bluurr.quora.domain.QuestionSearchResult;
import com.github.webdriverextensions.WebComponent;

/**
 * Sub component of each question with Quora search page results.
 *
 * @author Bluurr
 *
 */
public class SearchResultComponent extends WebComponent {

	@FindBy(xpath = "//*[contains(@class, 'puppeteer_test_question_title')]/span")
	private WebElement question;

	@FindBy(xpath = ".//*[@href]")
	private WebElement location;

	public QuestionSearchResult getSummary() {
		return QuestionSearchResult.builder()
				.title(question.getText())
				.location(location.getAttribute("href"))
				.build();
	}
}
