package com.bluurr.quora.page.question;

import com.bluurr.quora.domain.Answer;
import com.bluurr.quora.domain.Question;
import com.bluurr.quora.extension.EnhancedDriver;
import com.bluurr.quora.page.PageObject;
import com.bluurr.quora.page.component.InfiniteScrollPage;
import com.bluurr.quora.page.question.QuestionAnswerComponent.QuestionAnswerComponentDriverAware;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Question page for Quora once logged in.
 */
@Slf4j
public class QuestionPage extends PageObject {

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

  public QuestionAnswersInfiniteScrollPage<Answer> answers() {
    return new QuestionAnswersInfiniteScrollPage<>(QuestionAnswerComponentDriverAware::getAnswer);
  }

  public QuestionAnswersInfiniteScrollPage<Supplier<Answer>> deferAnswers() {

    Function<QuestionAnswerComponentDriverAware, Supplier<Answer>> apply = component -> component::getAnswer;

    return new QuestionAnswersInfiniteScrollPage<>(apply);
  }

  private String getTitle() {

    return titles.stream()
        .findFirst()
        .map(WebElement::getText)
        .orElse("");
  }

  @RequiredArgsConstructor
  class QuestionAnswersInfiniteScrollPage<T> implements InfiniteScrollPage<T> {

    private final Function<QuestionAnswerComponentDriverAware, T> answerOutput;

    @Override
    public List<T> resultsWithSkip(final int skip) {

      return answerComponents.stream()
          .skip(skip)
          .filter(QuestionAnswerComponent::hasParagraphs)
          .map(component -> component.withDriver(driver()))
          .map(answerOutput)
          .collect(Collectors.toList());
    }

    @Override
    public void scrollNext() {
      var currentAmount = answerComponents.size();

      driver().scrollToPageBottom();
      driver().waitForNumberOfElementsToBeMoreThan(currentAmount, answerComponents);
    }
  }
}
