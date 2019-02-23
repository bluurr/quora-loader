package com.bluurr.quora.it.page;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import javax.annotation.Resource;

import org.hamcrest.core.Every;
import org.junit.Before;
import org.junit.Test;

import com.bluurr.quora.domain.LoginCredential;
import com.bluurr.quora.domain.QuestionSummary;
import com.bluurr.quora.it.BaseIntegrationTest;
import com.bluurr.quora.page.LoginPage;
import com.bluurr.quora.page.search.SearchPage;

/**
 * 
 * @author Bluurr
 *
 */
public class SearchPageIT extends BaseIntegrationTest {
	/**
	 * Likely to always return topics.
	 */
	private static final String SEARCH_TERM = "Java";
	private static final String SEARCH_TYPE = "answer";
	
	@Resource
	private LoginCredential credential;
	
	@Before
	public void before() {
		LoginPage.open(QUORA_HOST).login(credential);
	}

	@Test
	public void performSearch() {
		SearchPage page = SearchPage.openDirect(SEARCH_TERM, SEARCH_TYPE);
		assertThat(page, notNullValue());
		
		List<QuestionSummary> questions = page.getQuestions(5);
		assertThat(questions, notNullValue());
		assertThat(questions, Every.everyItem(notNullValue(QuestionSummary.class)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void performInvalidSearch() {
		SearchPage.openDirect("", SEARCH_TYPE);
	}
}
