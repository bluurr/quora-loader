package com.bluurr.quora.it.page;

import com.bluurr.quora.domain.LoginCredential;
import com.bluurr.quora.it.BaseIntegrationTest;
import com.bluurr.quora.page.DashBoardPage;
import com.bluurr.quora.page.LoginPage;
import com.bluurr.quora.page.search.SearchPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 
 * @author Bluurr
 *
 */
class DashBoardPageIT extends BaseIntegrationTest {
	/**
	 * Likely to always return topics.
	 */
	private static final String SEARCH_TERM = "Java";
	
	@Resource
	private LoginCredential credential;
	
	@BeforeEach
	void before() {
		LoginPage.open(QUORA_HOST).login(credential);
	}
	
	@Test
	void performSearch() {
		DashBoardPage dashboard = DashBoardPage.open();
		SearchPage page = dashboard.search(SEARCH_TERM);
		assertThat(page, is(notNullValue()));
	}
	
	@Test
	void performInvalidSearch() {
		DashBoardPage dashboard = DashBoardPage.open();
		Assertions.assertThrows(IllegalArgumentException.class, () -> dashboard.search(""));
	}
}
