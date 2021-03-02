package com.bluurr.quora.page.question;

import com.bluurr.quora.domain.Answer;
import com.bluurr.quora.extension.EnhancedDriver;
import com.github.webdriverextensions.WebComponent;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Sub component of each answer with Quora question page.
 */
public class QuestionAnswerComponent extends WebComponent {

  @FindAll( {
      @FindBy(xpath = ".//*[contains(@class, 'puppeteer_test_answer_content')]//p//span"),
      @FindBy(xpath = ".//*[contains(@class, 'puppeteer_test_answer_content')]//ul//li//span")
  })
  private List<WebElement> paragraphs;

  @FindBy(xpath = ".//*[contains(@class, 'puppeteer_test_answer_content')]//span//span[text()='(more)']")
  private List<WebElement> readMore;

  @FindBy(xpath = ".//a[@class='user']")
  private List<WebElement> answerBy;

  public QuestionAnswerComponentDriverAware withDriver(final EnhancedDriver driver) {
    return new QuestionAnswerComponentDriverAware(driver);
  }

  @RequiredArgsConstructor
  class QuestionAnswerComponentDriverAware {

    private final EnhancedDriver driver;

    public Answer getAnswer() {

      readMore
          .stream()
          .findFirst()
          .ifPresent(this::expandReadMore);


      return Answer.builder()
          .answerBy(toUsername())
          .paragraphs(toParagraphs(paragraphs))
          .build();
    }

    private String toUsername() {

      return answerBy.stream()
          .findFirst()
          .map(WebElement::getText)
          .orElse("");
    }

    private List<String> toParagraphs(final List<WebElement> messages) {

      return messages.stream()
          .map(WebElement::getText)
          .collect(Collectors.toList());
    }

    private void expandReadMore(final WebElement clickTarget) {

      // Javascript click due to native clicking unable to execute.
      driver.executeJavascript("arguments[0].click();", clickTarget);
    }
  }
}
