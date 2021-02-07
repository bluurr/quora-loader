package com.bluurr.quora.page.question;

import com.bluurr.quora.domain.Answer;
import com.bluurr.quora.domain.Question;
import com.bluurr.quora.page.PageObject;
import com.github.webdriverextensions.Bot;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

import static com.bluurr.quora.extension.BotExtra.scrollToPageBottom;
import static com.bluurr.quora.extension.BotExtra.waitForNumberOfElementsToBeMoreThan;

/**
 * Question page for Quora once logged in.
 *
 * @author Bluurr
 *
 */
@Slf4j
public class QuestionPage extends PageObject {


	@FindBy(xpath = "//*[contains(@class, 'puppeteer_test_question_title')]//*")
	private List<WebElement> titles;

	@FindBy(xpath="//*[contains(@class, 'CssComponent')]/*[contains(@class, 'q-box')]")
	private List<QuestionAnswerComponent> answers;

	@Getter
	private final QuestionAnswerFetch answerFetcher;

	public QuestionPage() {
		super();
		this.answerFetcher = new QuestionAnswerFetch();

	}

	public QuestionAnswerFetch answers() {
		return answerFetcher;
	}

	public Question question() {

		return Question.builder()
				.location(Bot.currentUrl())
				.title(getTitle())
				.build();
	}

	private String getTitle() {

		return titles.stream()
				.findFirst()
				.map(WebElement::getText)
				.orElse("");
	}



	public class QuestionAnswerFetch {

		private int seenIndex;

		public List<Answer> next() {

			int start = seenIndex;
			seenIndex = answers.size();

			scrollToPageBottom();
			waitForNumberOfElementsToBeMoreThan(seenIndex, answers);

			return answers.stream()
					.skip(start)
					.limit(seenIndex)
					.map(QuestionAnswerComponent::getAnswer)
					.filter(answer -> !answer.getParagraphs().isEmpty())
					.collect(Collectors.toList());
		}
	}
}
