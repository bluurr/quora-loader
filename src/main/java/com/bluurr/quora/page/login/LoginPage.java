package com.bluurr.quora.page.login;

import com.bluurr.quora.domain.user.LoginCredential;
import com.bluurr.quora.domain.user.UserSession;
import com.bluurr.quora.page.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

import static com.bluurr.quora.extension.BotExtra.waitForOneDisplay;

/**
 * Login page for Quora.
 */
public class LoginPage extends PageObject {

	@FindBy(xpath="//input[contains(@class, 'header_login_text_box') and @name = 'email']")
	private WebElement username;

	@FindBy(xpath="//input[contains(@class, 'header_login_text_box') and @name = 'password']")
	private WebElement password;

	@FindBy(xpath="//input[contains(@class, 'submit_button') and @value = 'Login']")
	private WebElement submitButton;

	@FindBy(xpath="//*[@id='root']")
	private List<WebElement> homePage;

	@FindBy(xpath="//*[@class='regular_login']//*[@class='input_validation_error_text']")
	private List<WebElement> validationErrors;

	public UserSession login(final LoginCredential credentials) {

		loginWith(credentials);

		if(isNotLoggedIn()) {
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

		waitForOneDisplay(List.of(username));

		username.clear();
		username.sendKeys(loginUsername);
	}

	private void enterPassword(final String loginPassword) {

		waitForOneDisplay(List.of(password));

		password.clear();
		password.sendKeys(loginPassword);
	}

	private void submitAndWaitForLogin() {
		submitButton.click();
		waitForLogin();
	}

	private boolean isNotLoggedIn() {
		return !validationErrors.isEmpty();
	}

	private void waitForLogin() {

		waitForOneDisplay(homePage, validationErrors);

		if(isNotLoggedIn()) {
			throw new InvalidLoginException(getErrorReason());
		}
	}

	private String getErrorReason() {
		var messages =  validationErrors.stream().map(WebElement::getText).collect(Collectors.joining(", "));
		return messages.isEmpty() ? "Unable to login." : messages;
	}
}
