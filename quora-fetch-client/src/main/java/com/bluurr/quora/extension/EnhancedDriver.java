package com.bluurr.quora.extension;

import com.github.webdriverextensions.WebDriverExtensionsContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.Closeable;
import java.util.List;

@RequiredArgsConstructor
public class EnhancedDriver implements Closeable {

  private static final int DEFAULT_TIMEOUT_SECONDS = 8;
  private static final int DEFAULT_WAIT_MILLISECOND = 500;

  @Getter
  private final String baseUrl;

  @Accessors(fluent = true)
  @Getter
  private final WebDriver webDriver;

  public void scrollToPageBottom() {

    executeJavascript("window.scrollBy(0, document.body.scrollHeight)", "");
  }

  public void executeJavascriptClick(final WebElement element) {
    executeJavascript("arguments[0].click()", element);
  }

  public void executeJavascript(final String script, final Object... args) {

    ((JavascriptExecutor) webDriver()).executeScript(script, args);
  }

  public void waitForNumberOfElementsToBeMoreThan(final int startSize, final List<?> elements) {

    waiting().until(driver -> elements.size() > startSize);
  }

  public void waitForDisplay(final WebElement element) {

    waiting().until(driver -> isDisplayed(element));
  }

  public void waitForOneDisplay(final List<WebElement> groupA, final List<WebElement> groupB) {

    waiting().until(driver -> anyDisplayed(groupA) || anyDisplayed(groupB));
  }

  @Override
  public void close() {

    WebDriverExtensionsContext.removeDriver();
    webDriver.close();
  }


  private boolean anyDisplayed(final List<WebElement> elements) {

    return elements.stream().anyMatch(this::isDisplayed);
  }

  private boolean isDisplayed(final WebElement element) {

    try {
      return element.isDisplayed();
    } catch (NoSuchElementException var2) {
      return false;
    }
  }

  private WebDriverWait waiting() {

    return new WebDriverWait(webDriver(), DEFAULT_TIMEOUT_SECONDS, DEFAULT_WAIT_MILLISECOND);
  }
}
