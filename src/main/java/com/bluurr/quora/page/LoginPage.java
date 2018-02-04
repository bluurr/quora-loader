package com.bluurr.quora.page;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bluurr.quora.domain.LoginCredential;
import com.github.webdriverextensions.Bot;

/**
 * Login page for quora.
 * 
 * @author chris
 *
 */
public class LoginPage extends PageObject
{
	public static boolean isLoggedIn()
	{
		WebElement body = Bot.driver().findElement(By.tagName("body"));
		return !Arrays.asList(body.getAttribute("class").split(" ")).contains("logged_out");
	}
	
	public static LoginPage open(final URI location)
	{
		try 
		{
			Bot.driver().navigate().to(location.toURL());
		} catch (final MalformedURLException err) 
		{
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
	
	public DashBoardPage login(final LoginCredential credential)
	{
		username.clear();
		username.sendKeys(credential.getUsername());

		password.clear();
		password.sendKeys(credential.getPassword());
		
		submitButton.click();
		
		return DashBoardPage.open();
	}
}