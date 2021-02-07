package com.bluurr.quora.it.page;

import com.bluurr.quora.Navigator;
import com.bluurr.quora.domain.Question;
import com.bluurr.quora.domain.QuestionSearchResult;
import com.bluurr.quora.it.BaseIntegrationTest;
import com.bluurr.quora.page.search.SearchPage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Every.everyItem;
@Slf4j
class QuestionPageIT extends BaseIntegrationTest {

	private static final String SEARCH_TERM = "java";

	@Resource
	private Navigator navigator;

	private QuestionSearchResult summary;

	@BeforeEach
	void before() {

		navigator.login();
		SearchPage page = navigator.searchForTerm(SEARCH_TERM);
		summary = page.next().get(0);

	}

	@Test
	void loadQuestion() {

		var page = navigator.openQuestion(summary.getLocation());

		assertThat(page, notNullValue());

		Question question = page.question();

		assertThat(question, notNullValue());
		assertThat(question.getTitle(), notNullValue());
		assertThat(question.getLocation(), notNullValue());

		var answers = page.answers().next();

		assertThat(answers, notNullValue());
		assertThat(answers, everyItem(notNullValue()));
	}
}
