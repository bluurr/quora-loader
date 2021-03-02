package com.bluurr.quora.navigator;

import com.bluurr.quora.domain.user.LoginCredential;
import com.bluurr.quora.extension.EnhancedDriver;
import com.bluurr.quora.page.login.LoginPage;

public class LoginPageNavigator {

  private final String baseUri;
  private final LoginCredential credentials;
  private final EnhancedDriver driver;
  private final LoginPage loginPage;

  public LoginPageNavigator(final String baseUri, final LoginCredential credentials, final EnhancedDriver driver) {
    this.baseUri = baseUri;
    this.credentials = credentials;
    this.driver = driver;
    this.loginPage = new LoginPage(driver);
  }

  public AuthenticatedNavigator authenticate() {

    driver.webDriver().navigate().to(baseUri);

    var session = loginPage.login(credentials);

    return new AuthenticatedNavigator(session, driver);
  }
}
