package com.bluurr.quora.navigator;

import com.bluurr.quora.domain.Answer;
import com.bluurr.quora.domain.Question;
import com.bluurr.quora.extension.EnhancedDriver;
import com.bluurr.quora.page.component.InfiniteScrollIterator;
import com.bluurr.quora.page.question.QuestionPage;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class QuestionPageNavigator {

  private final QuestionPage page;

  public QuestionPageNavigator(final EnhancedDriver driver) {
    this.page = new QuestionPage(driver);
  }

  public Question getQuestion() {
    return page.getQuestion();
  }

  public Iterator<List<Answer>> answers() {
    return new InfiniteScrollIterator<>(page.answers());
  }

  public Iterator<List<Supplier<Answer>>> deferAnswers() {
    return new InfiniteScrollIterator<>(page.deferAnswers());
  }
}
