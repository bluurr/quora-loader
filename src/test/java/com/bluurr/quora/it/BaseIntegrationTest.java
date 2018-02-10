package com.bluurr.quora.it;

import java.net.URI;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import com.bluurr.quora.extension.BotExtra;
import com.bluurr.quora.it.config.IntegrationConfig;

/**
 * 
 * @author Bluurr
 *
 */
@ContextConfiguration(classes=IntegrationConfig.class)
public abstract class BaseIntegrationTest 
{
	public static final URI QUORA_HOST = URI.create("https://www.quora.com");
	
	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();
	
	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();
	
	@Resource
	private ChromeOptions options;
	
	@Before
	public void beforeTest()
	{
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
		BotExtra.setDriver(driver);
	}
	
	@After
	public void afterTest()
	{
		BotExtra.closeDriver();
	}
}
