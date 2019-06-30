package com.bluurr.quora.it.page;

import com.bluurr.quora.domain.LoginCredential;
import com.bluurr.quora.domain.QuestionSummary;
import com.bluurr.quora.it.BaseIntegrationTest;
import com.bluurr.quora.page.LoginPage;
import com.bluurr.quora.page.search.SearchPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Every.everyItem;

/**
 * 
 * @author Bluurr
 *
 */
class SearchPageIT extends BaseIntegrationTest {
	/**
	 * Likely to always return topics.
	 */
	private static final String SEARCH_TERM = "Java";
	private static final String SEARCH_TYPE = "answer";
	
	@Resource
	private LoginCredential credential;
	
	@BeforeEach
	void before() {
		LoginPage.open(QUORA_HOST).login(credential);
	}

	@Test
	void performSearch() {
		SearchPage page = SearchPage.openDirect(SEARCH_TERM, SEARCH_TYPE);
		assertThat(page, notNullValue());
		
		List<QuestionSummary> questions = page.getQuestions(5);
		assertThat(questions, notNullValue());
		assertThat(questions, everyItem(notNullValue(QuestionSummary.class)));
	}
	
	@Test
	void performInvalidSearch() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> SearchPage.openDirect("", SEARCH_TYPE));
	}
}
