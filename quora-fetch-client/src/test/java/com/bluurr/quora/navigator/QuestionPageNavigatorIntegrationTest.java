package com.bluurr.quora.navigator;

import com.bluurr.quora.BaseIntegrationTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.bluurr.quora.TestTag.USES_QUORA_PLATFORM;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Every.everyItem;

@Tag(USES_QUORA_PLATFORM)
class QuestionPageNavigatorIntegrationTest extends BaseIntegrationTest {

  private static final String SEARCH_TERM = "java";

  @Test
  void shouldGivenSearchTermWhenGetQuestionThenReturnAnswers() {

    // Given

    var authenticatedNav = authenticatedNavigator();

    var summary = authenticatedNav
        .searchForTerm(SEARCH_TERM)
        .firstResult()
        .orElseThrow();

    // When

    var page = authenticatedNav.getQuestionAt(summary.getLocation());

    // Then

    assertThat(page, notNullValue());

    var question = page.getQuestion();

    assertThat(question, notNullValue());
    assertThat(question.getTitle(), notNullValue());
    assertThat(question.getLocation(), notNullValue());

    var answers = page.answers().next();

    assertThat(answers, notNullValue());
    assertThat(answers, everyItem(notNullValue()));
  }

}
