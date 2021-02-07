package com.bluurr.quora.page.search;

import com.bluurr.quora.domain.QuestionSearchResult;
import com.bluurr.quora.page.PageObject;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

import static com.bluurr.quora.extension.BotExtra.scrollToPageBottom;
import static com.bluurr.quora.extension.BotExtra.waitForNumberOfElementsToBeMoreThan;

/**
 * Question search page for Quora once logged in.
 *
 * @author Bluurr
 *
 */
@Slf4j
public class SearchPage extends PageObject {

	@FindBy(xpath="//*[contains(@class, 'CssComponent')]//*[contains(@class, 'qu-fontSize--regular')]")
	private List<SearchResultComponent> results;

	private int seenIndex;

	public List<QuestionSearchResult> next() {

		int start = seenIndex;
		seenIndex = results.size();

		fetchNext();

		return results.stream()
				.skip(start)
				.limit(seenIndex)
				.map(SearchResultComponent::getSummary)
				.collect(Collectors.toList());
	}


	private void fetchNext() {

		var currentAmount = results.size();

		scrollToPageBottom();
		waitForNumberOfElementsToBeMoreThan(currentAmount, results);
	}
}
