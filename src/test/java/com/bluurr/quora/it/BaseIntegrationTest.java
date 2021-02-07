package com.bluurr.quora.it;

import com.bluurr.quora.extension.BotExtra;
import com.bluurr.quora.it.config.IntegrationConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.annotation.Resource;

@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(classes=IntegrationConfig.class)
public abstract class BaseIntegrationTest {

	@Resource
	@Container
	private BrowserWebDriverContainer<?> driverContainer;

	@BeforeEach
	public void beforeTest() {
		BotExtra.setDriver(driverContainer.getWebDriver());
	}

	@AfterEach
	public void afterTest() {
		BotExtra.closeDriver();
	}
}
