package com.bluurr.quora.it.config;

import java.io.File;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode;

import com.bluurr.quora.domain.LoginCredential;

/**
 * 
 * @author Bluurr
 *
 */
@Configuration
public class IntegrationConfig 
{
	@Bean
	public LoginCredential createLogin(final @Value("${quora.login.username}") String username,
			final @Value("${quora.login.password}") String password) 
	{
		return new LoginCredential(username, password);
	}

	@Bean
	public ChromeOptions createOptions(final @Value("${webdriver.headless:false}") boolean headless, 
			final @Value("${quora.contact}") String contactEmail) 
	{
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(headless);
		/**
		 * Quora TOS require the user agent to include a contact email.
		 */
		options.addArguments("--user-agent=quora_loader;" + contactEmail + ";");
		
		return options;
	}
	
	@SuppressWarnings("resource")
	@Bean
	public BrowserWebDriverContainer<?> createDriverContainer(final ChromeOptions options, final @Value("${container.record:false}")  boolean record) {
		BrowserWebDriverContainer<?> container = new BrowserWebDriverContainer<>().
				withDesiredCapabilities(new DesiredCapabilities(options));
		
		if(record) {
			container = container.withRecordingMode(VncRecordingMode.RECORD_ALL, new File("./"));
		} else
		{
			container.withRecordingMode(VncRecordingMode.SKIP, null);
		}
		return container;
	}
}
