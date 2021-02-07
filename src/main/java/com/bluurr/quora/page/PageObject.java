package com.bluurr.quora.page;

import com.github.webdriverextensions.Bot;
import com.github.webdriverextensions.WebDriverExtensionFieldDecorator;

import static org.openqa.selenium.support.PageFactory.initElements;

/**
 * Base for Selenium page object instances.
 *
 */
public abstract class PageObject {
	public PageObject() {
		initElements(new WebDriverExtensionFieldDecorator(Bot.driver()), this);
	}
}
