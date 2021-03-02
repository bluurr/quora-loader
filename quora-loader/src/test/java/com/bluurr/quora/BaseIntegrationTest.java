package com.bluurr.quora;

import com.bluurr.quora.config.IntegrationConfig;
import com.bluurr.quora.domain.user.LoginCredential;
import com.bluurr.quora.extension.EnhancedDriver;
import com.bluurr.quora.navigator.AuthenticatedNavigator;
import com.bluurr.quora.navigator.LoginPageNavigator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(classes = IntegrationConfig.class)
public abstract class BaseIntegrationTest {

  protected static final String BASE_URL = "https://www.quora.com";

  @Autowired
  @Container
  private BrowserWebDriverContainer<?> driverContainer;

  @Autowired
  private LoginCredential credential;

  @Accessors(fluent = true)
  @Getter(value = AccessLevel.PROTECTED)
  private EnhancedDriver driver;

  @BeforeEach
  public void beforeTest() {
    this.driver = new EnhancedDriver(BASE_URL, driverContainer.getWebDriver());
  }

  @AfterEach
  public void afterTest() {
    this.driver().close();
  }

  public AuthenticatedNavigator authenticatedNavigator() {
    var nav = new LoginPageNavigator(credential, driver);
    return nav.authenticate();
  }
}
