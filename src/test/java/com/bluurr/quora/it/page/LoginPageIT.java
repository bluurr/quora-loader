package com.bluurr.quora.it.page;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.annotation.Resource;

import com.bluurr.quora.domain.LoginCredential;
import com.bluurr.quora.it.BaseIntegrationTest;
import com.bluurr.quora.page.InvalidLoginException;
import com.bluurr.quora.page.LoginPage;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author Bluurr
 *
 */
class LoginPageIT extends BaseIntegrationTest {

	@Resource
	private LoginCredential credential;
	
	@Test
	void testValidLogin() {
		assertThat(LoginPage.isLoggedIn(), is(false));
		
		LoginPage.open(QUORA_HOST).login(credential);
		
		assertThat(LoginPage.isLoggedIn(), is(true));
	}
	
	@Test//(expected=InvalidLoginException.class)
	void testInvalidLogin() {
		assertThat(LoginPage.isLoggedIn(), is(false));
		
		LoginPage.open(QUORA_HOST).login(createInvalidLogin());
		
		assertThat(LoginPage.isLoggedIn(), is(false));
	}
	
	private LoginCredential createInvalidLogin() {
		return new LoginCredential("hyedjdfhudfhuu45y45", "ghdfhdfhyuhdfyhyudf");
	}
}