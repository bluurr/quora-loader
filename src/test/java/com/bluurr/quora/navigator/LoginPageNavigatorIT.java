package com.bluurr.quora.navigator;

import com.bluurr.quora.BaseIntegrationTest;
import com.bluurr.quora.domain.user.LoginCredential;
import com.bluurr.quora.domain.user.InvalidLoginException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginPageNavigatorIT extends BaseIntegrationTest {

  @Nested
  class ValidCredentialsTest {

    @Resource
    private LoginCredential credential;

    @Test
    void givenValidCredentialsWhenLoginThenLoginSuccessful() {

      // Given

      var navigator = new LoginPageNavigator(BASE_URL, credential, driver());

      // When

      var authenticated = navigator.authenticate();

      // Then
      assertThat(authenticated, notNullValue());
      assertThat(authenticated.getSession(), notNullValue());
      assertThat(authenticated.getSession().getUsername(), equalTo(credential.getUsername()));
    }
  }

  @Nested
  @TestPropertySource(properties = { "quora.login.username=hyedjdfhudfhuu45y45", "quora.login.password=ghdfhdfhyuhdfyhyudf" })
  class InvalidCredentialsTest {

    @Resource
    private LoginCredential credential;

    @Test
    void givenInvalidCredentialsWhenLoginThenThrowInvalidLogin() {

      // Given

      var navigator = new LoginPageNavigator(BASE_URL, credential, driver());

      // When

      var error = assertThrows(InvalidLoginException.class, navigator::authenticate);

      // Then

      assertThat(error.getMessage(), equalTo("No account found for this email. Retry, or Sign up for Quora."));
    }
  }
}
