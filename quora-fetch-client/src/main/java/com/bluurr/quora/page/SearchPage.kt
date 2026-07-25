package com.bluurr.quora.page;

import com.bluurr.quora.extension.EnhancedDriver
import com.bluurr.quora.extension.href
import com.bluurr.quora.model.SearchQuestion
import com.bluurr.quora.model.SearchResult
import com.github.webdriverextensions.WebComponent
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

/**
 * Question search page for Quora once logged in.
 */
class SearchPage(private val driver: EnhancedDriver) {

    private val page = PageObject().also {
        driver.hydrate(it)
    }

    fun findSearchResult() : Sequence<SearchResult> {

        var currentQuestionCount = 0

        return sequence {

            while (currentQuestionCount < page.resultsComponent.size) {

                page.resultsComponent
                    .elementAt(currentQuestionCount++)
                    .let { SearchResult(SearchQuestion(it.question.text, it.location.href())) }
                    .also { scrollToNextQuestion(currentQuestionCount) }
                    .run { yield(this) }
            }
        }
    }

    private fun scrollToNextQuestion(currentOffset: Int) {

        if (currentOffset < page.resultsComponent.size) {
            return
        }

        driver.scrollToPageBottom()
        driver.waitForNumberOfElementsToBeMoreThan(currentOffset, page.resultsComponent)
    }

    private class PageObject {

        @FindBy(xpath = "//*[contains(@id, 'mainContent')]//*[contains(@class, 'q-box') and contains(@class, 'qu-pb--tiny')]")
        lateinit var resultsComponent: List<SearchResultPageComponent>
    }

    class SearchResultPageComponent : WebComponent() {

        @FindBy(xpath = ".//*[contains(@class, 'puppeteer_test_question_title')]/span")
        lateinit var question: WebElement

        @FindBy(xpath = ".//*[@href]")
        lateinit var location: WebElement
    }
}
