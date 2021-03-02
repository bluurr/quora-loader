package com.bluurr.quora.page.question;

import com.bluurr.quora.domain.Answer;
import com.bluurr.quora.domain.Question;
import com.bluurr.quora.extension.EnhancedDriver;
import com.bluurr.quora.page.PageObject;
import com.bluurr.quora.page.component.InfiniteScrollPage;
import com.bluurr.quora.page.question.QuestionAnswerComponent.QuestionAnswerComponentDriverAware;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Question page for Quora once logged in.
 */
@Slf4j
public class QuestionPage extends PageObject implements InfiniteScrollPage<Answer> {

  @FindBy(xpath = "//*[contains(@class, 'puppeteer_test_question_title')]//*")
  private List<WebElement> titles;

  @FindBy(xpath = "//*[contains(@class, 'CssComponent')]/*[contains(@class, 'q-box')]")
  private List<QuestionAnswerComponent> answerComponents;

  public QuestionPage(final EnhancedDriver driver) {
    super(driver);
  }

  public Question getQuestion() {

    return Question.builder()
        .location(driver().webDriver().getCurrentUrl())
        .title(getTitle())
        .build();
  }

  private String getTitle() {

    return titles.stream()
        .findFirst()
        .map(WebElement::getText)
        .orElse("");
  }

  @Override
  public int currentElementCount() {
    return answerComponents.size();
  }

  @Override
  public List<Answer> resultsWithSkip(final int skip) {
    return answerComponents.stream()
        .skip(skip)
        .map(component -> component.withDriver(driver()))
        .map(QuestionAnswerComponentDriverAware::getAnswer)
        .filter(answer -> !answer.getParagraphs().isEmpty())
        .collect(Collectors.toList());
  }

  @Override
  public void scrollNext() {
    var currentAmount = answerComponents.size();

    driver().scrollToPageBottom();
    driver().waitForNumberOfElementsToBeMoreThan(currentAmount, answerComponents);
  }
}
