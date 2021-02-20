package com.bluurr.quora.navigator;

import com.bluurr.quora.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class SearchPageNavigatorIT extends BaseIntegrationTest {

  private static final String SEARCH_TERM = "Java";

  @Test
  void shouldGivenSearchTermWhenSearchThenResultReturned() {

    // Given

    var authenticatedNav =  authenticatedNavigator();

    // When

    var searchNav = authenticatedNav.searchForTerm(SEARCH_TERM);

    // Given

    assertThat(searchNav, notNullValue());

    var question = searchNav.firstResult();

    assertThat(question, notNullValue());
  }

  @Test
  void shouldGivenSearchTermWhenMultipleSearchResultsThenResultReturned() {

    // Given

    var authenticatedNav =  authenticatedNavigator();

    // When

    var searchNav = authenticatedNav.searchForTerm(SEARCH_TERM);

    // Given

    assertThat(searchNav, notNullValue());

    var question = searchNav.results().next();

    assertThat(question, notNullValue());
    assertThat(question, everyItem(notNullValue()));
  }

  @Test
  void shouldGivenNullSearchTermWhenSearchThenThrowError() {

    // Given

    var authenticatedNav =  authenticatedNavigator();

    // When

    var result = assertThrows(NullPointerException.class,
        () -> authenticatedNav.searchForTerm(null));

    // Then

    assertThat(result.getMessage(), equalTo("term is marked non-null but is null"));
  }

}
