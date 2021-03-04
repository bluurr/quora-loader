package com.bluurr.quora.page.login;

import com.bluurr.quora.model.user.InvalidLoginException;
import com.bluurr.quora.model.user.LoginCredential;
import com.bluurr.quora.model.user.UserSession;
import com.bluurr.quora.extension.EnhancedDriver;
import com.bluurr.quora.page.PageObject;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Login page for Quora.
 */
@Slf4j
public class LoginPage extends PageObject {

  @FindBy(xpath = "//input[@name = 'email']")
  private WebElement username;

  @FindBy(xpath = "//input[@name = 'password']")
  private WebElement password;

  @FindBy(xpath = "//button[child::*//*[text() = 'Login'] and not(contains(@class, 'qu-disabled'))]")
  private WebElement submitButton;

  @FindBy(xpath = "//*[@placeholder='Search Quora']")
  private List<WebElement> homePage;

  @FindBy(xpath = "//*[contains(@class, 'qu-color--red_error')]")
  private List<WebElement> validationErrors;

  public LoginPage(final EnhancedDriver driver) {
    super(driver);
  }

  public UserSession login(final LoginCredential credentials) {

    loginWith(credentials);

    if (hasLoginError()) {
      throw new InvalidLoginException(getErrorReason());
    }

    return UserSession.builder()
        .username(credentials.getUsername())
        .build();

  }

  private void loginWith(final LoginCredential credential) {
    enterUsername(credential.getUsername());
    enterPassword(credential.getPassword());
    submitAndWaitForLogin();
  }

  private void enterUsername(final String loginUsername) {

    driver().waitForDisplay(username);

    username.clear();
    username.sendKeys(loginUsername);
  }

  private void enterPassword(final String loginPassword) {

    driver().waitForDisplay(password);

    password.clear();
    password.sendKeys(loginPassword);
  }

  private void submitAndWaitForLogin() {

    try {

      driver().waitForDisplay(submitButton);
      driver().executeJavascriptClick(submitButton);

    } catch (final WebDriverException err) {
      log.info("submit button never enabled", err);
    }

    waitForLogin();
  }

  private boolean hasLoginError() {
    return !validationErrors.isEmpty();
  }

  private void waitForLogin() {

    driver().waitForOneDisplay(homePage, validationErrors);

    if (hasLoginError()) {
      throw new InvalidLoginException(getErrorReason());
    }
  }

  private String getErrorReason() {

    var messages = validationErrors.stream()
        .map(WebElement::getText)
        .collect(Collectors.joining(", "));

    return messages.isEmpty() ? "Unable to login." : messages;
  }
}
