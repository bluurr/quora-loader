package com.bluurr.quora.client

import com.bluurr.quora.config.WebDriverConfiguration.DriverFactory
import com.bluurr.quora.model.Answer
import com.bluurr.quora.model.QuestionSearchResult
import com.bluurr.quora.model.user.LoginCredential
import com.bluurr.quora.model.user.UserSession
import com.bluurr.quora.navigator.AuthenticatedNavigator
import com.bluurr.quora.navigator.LoginPageNavigator

class BaseQuoraClient(private val credentials: LoginCredential, driverFactory: DriverFactory): QuoraClient {

    private var session: UserSession? = null

    private val driver = lazy {
        driverFactory.createDriver()
    }

    override fun findQuestionsForTerm(term: String): Sequence<QuestionSearchResult> {

        val authenticatedNav = authenticatedNavigator()

        val questionResults = authenticatedNav.searchForTerm(term).results()

        return pages(questionResults).flatten()
    }

    override fun getAnswersForQuestionAt(href: String): Sequence<Answer> {

        val authenticatedNavigator = authenticatedNavigator()

        val questionAnswers = authenticatedNavigator.getQuestionAt(href).deferAnswers()

        return pages(questionAnswers).flatten().map {
            it.get()
        }
    }

    override fun close() {

       if (driver.isInitialized()) {
           driver.value.close()
       }
    }

    private fun authenticatedNavigator(): AuthenticatedNavigator {

        return session?.let {
            AuthenticatedNavigator(it, driver.value)
        } ?: authenticate()
    }

    private fun authenticate() : AuthenticatedNavigator {

        val login = LoginPageNavigator(credentials, driver.value)

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
