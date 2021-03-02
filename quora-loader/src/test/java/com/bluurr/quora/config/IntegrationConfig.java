package com.bluurr.quora.config;

import com.bluurr.quora.domain.user.LoginCredential;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode;

import java.io.File;

@Configuration
public class IntegrationConfig {

  @Bean
  public LoginCredential createLogin(final @Value("${quora.login.username}") String username,
                                     final @Value("${quora.login.password}") String password) {

    return LoginCredential.builder()
        .username(username)
        .password(password)
        .build();
  }

  @Bean
  public ChromeOptions createOptions(final @Value("${webdriver.headless:true}") boolean headless,
                                     final @Value("${quora.contact}") String contactEmail) {
    ChromeOptions options = new ChromeOptions();
    options.setHeadless(headless);
    /**
     * Quora TOS require the user agent to include a contact email.
     */
    options.addArguments("--user-agent=quora_loader;" + contactEmail + ";");

    return options;
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public BrowserWebDriverContainer<?> createDriverContainer(final ChromeOptions options, final @Value("${container.record:false}") boolean record) {
    BrowserWebDriverContainer<?> container = new BrowserWebDriverContainer<>().
        withCapabilities(new DesiredCapabilities(options));

    if (record) {
      container = container.withRecordingMode(VncRecordingMode.RECORD_ALL, new File("./"));
    } else {
      container.withRecordingMode(VncRecordingMode.SKIP, null);
    }
    return container;
  }
}
