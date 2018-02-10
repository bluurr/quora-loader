package com.bluurr.quora.it.config;

import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bluurr.quora.domain.LoginCredential;

/**
 * 
 * @author Bluurr
 *
 */
@Configuration
public class IntegrationConfig {
	@Bean
	public LoginCredential createLogin(final @Value("${quora.login.username}") String username,
			final @Value("${quora.login.password}") String password) {
		return new LoginCredential(username, password);
	}

	@Bean
	public ChromeOptions createOptions(final @Value("${webdriver.headless:false}") boolean headless, 
			final @Value("${quora.contact}") String contactEmail) 
	{
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(headless);
		/**
		 * Quroa TOS require detail user agent and contact email.
		 */
		options.addArguments("--user-agent=quroa_loader;" + contactEmail + ";");
		
		return options;
	}
}
