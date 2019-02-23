package com.bluurr.quora.it.page;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.bluurr.quora.domain.LoginCredential;
import com.bluurr.quora.it.BaseIntegrationTest;
import com.bluurr.quora.page.DashBoardPage;
import com.bluurr.quora.page.LoginPage;
import com.bluurr.quora.page.search.SearchPage;

/**
 * 
 * @author Bluurr
 *
 */
public class DashBoardPageIT extends BaseIntegrationTest {
	/**
	 * Likely to always return topics.
	 */
	private static final String SEARCH_TERM = "Java";
	
	@Resource
	private LoginCredential credential;
	
	@Before
	public void before() {
		LoginPage.open(QUORA_HOST).login(credential);
	}
	
	@Test
	public void performSearch() {
		DashBoardPage dashboard = DashBoardPage.open();
		SearchPage page = dashboard.search(SEARCH_TERM);
		assertThat(page, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void performInvalidSearch() {
		DashBoardPage dashboard = DashBoardPage.open();
		dashboard.search("");
	}
}
