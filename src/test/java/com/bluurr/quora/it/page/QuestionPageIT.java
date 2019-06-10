package com.bluurr.quora.it.page;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.annotation.Resource;

import org.hamcrest.core.Every;
import org.junit.Before;
import org.junit.Test;

import com.bluurr.quora.domain.Answer;
import com.bluurr.quora.domain.Answers;
import com.bluurr.quora.domain.LoginCredential;
import com.bluurr.quora.domain.Question;
import com.bluurr.quora.domain.QuestionSummary;
import com.bluurr.quora.it.BaseIntegrationTest;
import com.bluurr.quora.page.LoginPage;
import com.bluurr.quora.page.question.QuestionPage;
import com.bluurr.quora.page.search.SearchPage;

/**
 * 
 * @author Bluurr
 *
 */
public class QuestionPageIT extends BaseIntegrationTest {
	/**
	 * Likely to always return topics.
	 */
	private static final String SEARCH_TERM = "Java";

	@Resource
	private LoginCredential credential;
	
	private QuestionSummary summary;
	
	@Before
	public void before() {
		SearchPage page = LoginPage.open(QUORA_HOST).login(credential).search(SEARCH_TERM);
		summary = page.getQuestions(1).get(0);
	}

	@Test
	public void loadQuestion() {
		QuestionPage page = QuestionPage.open(summary.getLocation());
		assertThat(page, notNullValue());
		Question question = page.getQuestion(Answers.limit(5));
		assertThat(question, notNullValue());
		assertThat(question.getAnswers(), notNullValue());
		assertThat(question.getAnswers(), Every.everyItem(notNullValue(Answer.class)));
	}

}
