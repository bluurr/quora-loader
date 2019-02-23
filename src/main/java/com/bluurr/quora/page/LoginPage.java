package com.bluurr.quora.page;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bluurr.quora.domain.LoginCredential;
import com.bluurr.quora.extension.BotExtra;
import com.github.webdriverextensions.Bot;

/**
 * Login page for Quora.
 * 
 * @author Bluurr
 *
 */
public class LoginPage extends PageObject {
	public static boolean isLoggedIn() {
		WebElement body = Bot.driver().findElement(By.tagName("body"));
		
		boolean loggedIn = Bot.hasNotClass("logged_out", body);
		
		if(loggedIn) {
			/** Ensure that we have a valid Quora web page loaded */
			loggedIn = Bot.hasClass("web_page", body);
		}
		
		return loggedIn;
	}
	
	public static LoginPage open(final URI location) {
		try {
			Bot.driver().navigate().to(location.toURL());
		} catch (final MalformedURLException err) {
			throw new IllegalStateException("Unable to load login page", err);
		}
		
		return new LoginPage();
	}
	
	@FindBy(xpath="//input[contains(@class, 'header_login_text_box') and @name = 'email']")
	private WebElement username;
	
	@FindBy(xpath="//input[contains(@class, 'header_login_text_box') and @name = 'password']")
	private WebElement password;
	
	@FindBy(xpath="//input[contains(@class, 'submit_button') and @value = 'Login']")
	private WebElement submitButton;
	
	@FindBy(className="LoggedInSiteHeader")
	private List<WebElement> loginHeader;
	
	@FindBy(xpath="//*[@class='regular_login']//*[@class='input_validation_error_text']")
	private List<WebElement> validationErrors;
	
	public DashBoardPage login(final LoginCredential credential) {
		username.clear();
		username.sendKeys(credential.getUsername());

		password.clear();
		password.sendKeys(credential.getPassword());
		
		submitButton.click();
		
		waitForLogin();
		return DashBoardPage.open();
	}
	
	private void waitForLogin() {
		BotExtra.waitForOneDisplay(loginHeader, validationErrors);

		if(!isLoggedIn()) {
			throw new InvalidLoginException(validationMessages());
		}
	}	
	
	private String validationMessages() {
		if(!validationErrors.isEmpty()) {
			String message = 
					validationErrors.stream().map(WebElement::getText).collect(Collectors.joining(", "));
			
			return message;
		} 
		
		return "Unable to login.";
	}
}