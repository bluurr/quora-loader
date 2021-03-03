package com.bluurr.quora.page.search;

import com.bluurr.quora.model.QuestionSearchResult;
import com.bluurr.quora.extension.EnhancedDriver;
import com.bluurr.quora.page.PageObject;
import com.bluurr.quora.page.component.InfiniteScrollPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Question search page for Quora once logged in.
 */
@Slf4j
public class SearchPage extends PageObject implements InfiniteScrollPage<QuestionSearchResult> {

  @FindBy(xpath = "//*[contains(@class, 'CssComponent')]//*[contains(@class, 'qu-fontSize--regular')]")
  private List<SearchResultPageComponent> resultsComponent;

  public SearchPage(final EnhancedDriver driver) {
    super(driver);
  }

  @Override
  public List<QuestionSearchResult> resultsWithSkip(final int skip) {
    return resultsComponent.stream()
        .skip(skip)
        .map(SearchResultPageComponent::getSummary)
        .collect(Collectors.toList());
  }

  @Override
  public void scrollNext() {
    var currentAmount = resultsComponent.size();

    driver().scrollToPageBottom();
    driver().waitForNumberOfElementsToBeMoreThan(currentAmount, resultsComponent);
  }
}
