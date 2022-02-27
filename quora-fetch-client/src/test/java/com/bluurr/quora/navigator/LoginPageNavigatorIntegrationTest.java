package com.bluurr.quora.navigator;

import com.bluurr.quora.BaseIntegrationTest;
import com.bluurr.quora.model.user.InvalidLoginException;
import com.bluurr.quora.model.user.LoginCredential;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import static com.bluurr.quora.TestTag.USES_QUORA_PLATFORM;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag(USES_QUORA_PLATFORM)
class LoginPageNavigatorIntegrationTest extends BaseIntegrationTest {

  @Nested
  class ValidCredentialsTest {

    @Autowired
    private LoginCredential credential;

    @Test
    void givenValidCredentialsWhenLoginThenLoginSuccessful() {

      // Given

      var navigator = new LoginPageNavigator(credential, driver());

      // When

      var authenticated = navigator.authenticate();

      // Then
      assertThat(authenticated, notNullValue());
      assertThat(authenticated.getSession(), notNullValue());
      assertThat(authenticated.getSession().getUsername(), equalTo(credential.getUsername()));
    }
  }

  @Nested
  @TestPropertySource(properties = {"quora.login.username=hyedjdfhudfhuu45y45", "quora.login.password=ghdfhdfhyuhdfyhyudf"})
  class InvalidCredentialsTest {

    @Autowired
    private LoginCredential credential;

    @Test
    void givenInvalidCredentialsWhenLoginThenThrowInvalidLogin() {

      // Given

      var navigator = new LoginPageNavigator(credential, driver());

      // When

      var error = assertThrows(InvalidLoginException.class, navigator::authenticate);

      // Then

      assertThat(error.getMessage(), equalTo("No account found for this email. Retry, or Sign up for Quora."));
    }
  }
}
