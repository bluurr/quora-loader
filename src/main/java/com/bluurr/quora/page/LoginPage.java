package com.bluurr.quora.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bluurr.quora.domain.LoginCredential;

/**
 * Login page for quora.
 * 
 * @author chris
 *
 */
public class LoginPage extends PageObject<LoginPage>
{
	@FindBy(xpath="//input[contains(@class, 'header_login_text_box') and @name = 'email']")
	private WebElement username;
	
	@FindBy(xpath="//input[contains(@class, 'header_login_text_box') and @name = 'password']")
	private WebElement password;
	
	@FindBy(xpath="//input[contains(@class, 'submit_button') and @value = 'Login']")
	private WebElement submitButton;
	
	public void login(final LoginCredential credential)
	{
		username.clear();
		username.sendKeys(credential.getUsername());

		password.clear();
		password.sendKeys(credential.getPassword());
		
		submitButton.click();
	}
}