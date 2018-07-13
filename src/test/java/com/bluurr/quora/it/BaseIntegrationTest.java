package com.bluurr.quora.it;

import java.net.URI;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.BrowserWebDriverContainer;

import com.bluurr.quora.extension.BotExtra;
import com.bluurr.quora.it.config.IntegrationConfig;

/**
 * 
 * @author Bluurr
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=IntegrationConfig.class)
public abstract class BaseIntegrationTest 
{
	public static final URI QUORA_HOST = URI.create("https://www.quora.com");
	
	@Resource
	@Rule
	public BrowserWebDriverContainer<?> driverContainer;
	
	@Before
	public void beforeTest()
	{
		driverContainer.getWebDriver().manage().deleteAllCookies();
		BotExtra.setDriver(driverContainer.getWebDriver());
	}
	
	@After
	public void afterTest()
	{
		BotExtra.closeDriver();
	}
}
