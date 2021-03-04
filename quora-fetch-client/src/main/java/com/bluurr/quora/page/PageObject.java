package com.bluurr.quora.page;

import com.bluurr.quora.extension.EnhancedDriver;
import com.github.webdriverextensions.WebDriverExtensionFieldDecorator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;

import static org.openqa.selenium.support.PageFactory.initElements;

/**
 * Base for Selenium page object instances.
 */
public abstract class PageObject {

  @Accessors(fluent = true)
  @Getter(value = AccessLevel.PROTECTED)
  private final EnhancedDriver driver;

  public PageObject(final EnhancedDriver driver) {
    this.driver = driver;
    initElements(new WebDriverExtensionFieldDecorator(driver.webDriver()), this);
  }
}
