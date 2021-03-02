package com.bluurr.quora.client

import com.bluurr.quora.domain.Answer
import com.bluurr.quora.domain.QuestionSearchResult
import com.bluurr.quora.domain.user.LoginCredential
import com.bluurr.quora.domain.user.UserSession
import com.bluurr.quora.extension.EnhancedDriver
import com.bluurr.quora.navigator.AuthenticatedNavigator
import com.bluurr.quora.navigator.LoginPageNavigator
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.stereotype.Component

@Component
class QuoraClient(private val credentials: LoginCredential) {

    companion object {
        const val quoraUrl = "https://www.quora.com/"
    }

    private var session: UserSession? = null

    private val driver: EnhancedDriver by lazy {
        EnhancedDriver(ChromeDriver())
    }

    fun fetchQuestionsForTerm(term: String): Sequence<QuestionSearchResult> {

        val authenticatedNav = authenticatedNavigator()

        val questionPage = authenticatedNav.searchForTerm(term).results()

        return pages(questionPage).flatten()
    }

    fun fetchAnswersForQuestionAt(href: String): Sequence<Answer> {

        val authenticatedNavigator = authenticatedNavigator()

        val answerPage = authenticatedNavigator.getQuestionAt(href).answers()

        return pages(answerPage).flatten()
    }

    private fun authenticatedNavigator(): AuthenticatedNavigator {

        return session?.let {
            AuthenticatedNavigator(it, driver)
        } ?: authenticate()
    }

    private fun authenticate() : AuthenticatedNavigator {

        val login = LoginPageNavigator(quoraUrl, credentials, driver)

        val authenticatedNav =  login.authenticate()

        // Copy over the session for later use.
        session = authenticatedNav.session

        return authenticatedNav
    }
}

fun <T>pages(page:Iterator<List<T>>) = sequence {

    while (page.hasNext()) {
        yield(page.next())
    }
}
