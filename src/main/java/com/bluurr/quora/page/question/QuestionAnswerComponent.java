package com.bluurr.quora.page.question;

import com.bluurr.quora.domain.Answer;
import com.github.webdriverextensions.WebComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Sub component of each answer with Quora question page.
 *
 * @author Bluurr
 *
 */
public class QuestionAnswerComponent extends WebComponent {

	@FindBy(xpath=".//*[contains(@class, 'puppeteer_test_answer_content')]//p//span")
	private List<WebElement> paragraphs;

	@FindBy(xpath = ".//*[contains(@class, 'puppeteer_test_answer_content')]//span//span[text()='(more)']")
	private List<WebElement> clickToExpand;

	@FindBy(xpath=".//a[@class='user']")
	private List<WebElement> answerBy;

	public Answer getAnswer() {

		if (canExpand()) {
			clickToExpand.get(0).click();

		}

		return Answer.builder()
				.answerBy(toUsername())
				.paragraphs(toParagraphs(paragraphs))
				.build();
	}

	private boolean canExpand() {
		return !clickToExpand.isEmpty();
	}

	private String toUsername() {
		return answerBy.stream()
				.findFirst()
				.map(WebElement::getText)
				.orElse("");
	}

	private List<String> toParagraphs(final List<WebElement> messages) {
		return messages.stream()
				.map(WebElement::getText)
				.collect(Collectors.toList());
	}
}
