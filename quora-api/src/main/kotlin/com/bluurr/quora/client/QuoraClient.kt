package com.bluurr.quora.client

import com.bluurr.quora.domain.Answer
import com.bluurr.quora.domain.QuestionSearchResult
import java.io.Closeable

interface QuoraClient : Closeable {
    fun findQuestionsForTerm(term: String): Sequence<QuestionSearchResult>
    fun getAnswersForQuestionAt(href: String): Sequence<Answer>
}
