package com.bluurr.quora.it.page;

import com.bluurr.quora.Navigator;
import com.bluurr.quora.domain.QuestionSearchResult;
import com.bluurr.quora.it.BaseIntegrationTest;
import com.bluurr.quora.page.search.SearchPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Every.everyItem;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

	@Resource
	private Navigator navigator;


	@BeforeEach
	void before() {
		navigator.login();
	}

	@Test
	void performSearch() {

		SearchPage page = navigator.searchForTerm(SEARCH_TERM);
		assertThat(page, notNullValue());

		List<QuestionSearchResult> questions = page.next();
		assertThat(questions, notNullValue());
		assertThat(questions, everyItem(notNullValue()));
	}

	@Test
	void performInvalidSearch() {

		var result = assertThrows(NullPointerException.class,
				() -> navigator.searchForTerm(null));

		assertThat(result.getMessage(), equalTo("term is marked non-null but is null"));
	}
}
