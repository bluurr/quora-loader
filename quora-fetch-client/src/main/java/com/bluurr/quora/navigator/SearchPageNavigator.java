package com.bluurr.quora.navigator;

import com.bluurr.quora.model.QuestionSearchResult;
import com.bluurr.quora.extension.EnhancedDriver;
import com.bluurr.quora.page.component.InfiniteScrollIterator;
import com.bluurr.quora.page.search.SearchPage;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class SearchPageNavigator {

  private final SearchPage page;

  public SearchPageNavigator(final EnhancedDriver driver) {
    this.page = new SearchPage(driver);
  }

  public Optional<QuestionSearchResult> firstResult() {

    return page.resultsWithSkip(0).stream().findFirst();
  }

  public Iterator<List<QuestionSearchResult>> results() {

    return new InfiniteScrollIterator<>(page);
  }
}
