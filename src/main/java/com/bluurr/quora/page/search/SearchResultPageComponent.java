package com.bluurr.quora.page.search;

import com.bluurr.quora.domain.QuestionSearchResult;
import com.github.webdriverextensions.WebComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Sub component of each question with Quora search page results.
 *
 */
public class SearchResultPageComponent extends WebComponent {

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
