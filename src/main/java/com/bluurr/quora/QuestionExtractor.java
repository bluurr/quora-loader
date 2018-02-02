package com.bluurr.quora;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bluurr.quora.domain.LoginCredential;
import com.bluurr.quora.domain.Question;
import com.bluurr.quora.domain.QuestionSummary;
import com.bluurr.quora.driver.WebDriverFactory;
import com.bluurr.quora.extension.BotExtra;
import com.bluurr.quora.page.DashBoardPage;
import com.bluurr.quora.page.LoginPage;
import com.bluurr.quora.page.question.QuestionPage;
import com.bluurr.quora.page.search.SearchPage;
import com.github.webdriverextensions.WebDriverExtensionsContext;

/**
 * Loads questions from the Quora platform.
 * 
 * @author chris
 *
 */
public class QuestionExtractor implements AutoCloseable
{
	private static final Logger LOG = LoggerFactory.getLogger(QuestionExtractor.class);
	
	private final LoginCredential credential;
	private final URI baseLocation;
	
	private final WebDriverFactory factory;
	private WebDriver driver;

	public QuestionExtractor(final LoginCredential credential, final URI baseLocation, final WebDriverFactory factory) 
	{
		this.credential = credential;
		this.baseLocation = baseLocation;
		this.factory = factory;
	}
	
	public List<QuestionSummary> search(final String term, final int pageCount)
	{
		searchPrecondition(term, pageCount);
		validateAndConnect();
		
		DashBoardPage dashboard = new DashBoardPage().get();
		SearchPage searchPage = dashboard.search(term);

		int pagesLeft = pageCount;

		while(searchPage.hasPending() && pagesLeft-- > 0)
		{
			fetchNextBlock(searchPage);
		}
		
		return searchPage.getSummary();
	}
	
	private void fetchNextBlock(final SearchPage page)
	{
		String pendingId = page.getTopPendingQuestionId();
		
		BotExtra.scrollToPageBottom();
		
		int maxAttempt = 5;
		
		while(pendingId.equalsIgnoreCase(page.getTopPendingQuestionId()))
		{
			if(maxAttempt-- < 1)
			{
				//TODO: clean up the exception
				throw new IllegalStateException("Unable to load next block: timeout.");
			}

			try 
			{
				Thread.sleep(1000);
			} catch (final InterruptedException err) 
			{
				throw new IllegalStateException("Error when waiting for new page question", err);
			}
		}
	}
	

	@Override
	public void close() 
	{
		if(driver != null)
		{
			driver.close();
		}
	}
	
	private void takeScreenshot()
	{
		if(isConnected())
		{
			TakesScreenshot screenShot = TakesScreenshot.class.cast(driver);
			File source  = screenShot.getScreenshotAs(OutputType.FILE);
			
			File copyLocation = new File("flow/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "/" + source.getName());
			try 
			{
				FileUtils.copyFile(source, copyLocation);
			} catch (final IOException err) 
			{
				LOG.error("Unable to copy file", err);
			}
			System.out.println(copyLocation.getAbsolutePath());
		}
	}
	
	private void searchPrecondition(final String term, final int pageCount) 
	{
		Objects.requireNonNull(term, "Search term must not be null.");
		
		if(term.length() < 2)
		{
			throw new IllegalArgumentException("Search term must be at least 2 characters in length.");
		}
		
		if(pageCount < 1 || pageCount > 10)
		{
			throw new IllegalArgumentException("Page count must be between 1 and 10 in size.");
		}
	}
	
	private void validateAndConnect()
	{
		if(driver == null)
		{
			this.driver = factory.get();
			WebDriverExtensionsContext.setDriver(this.driver);
			this.driver.manage().deleteAllCookies();

			try 
			{
				this.driver.navigate().to(baseLocation.toURL());
			} catch (final MalformedURLException err) 
			{
				throw new IllegalStateException("Unable to connect to base URL", err);
			}
			
			try 
			{
				new LoginPage().login(credential);
			} finally
			{
				takeScreenshot();
			}
		}
	}
	
	private boolean isConnected()
	{
		return driver != null;
	}

	public Question getQuestion(final String location) 
	{
		QuestionPage page = new QuestionPage();
		page.openQuestion(location);
		
		Question question = page.getQuestion();
		return question;
	}
}