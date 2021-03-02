package com.bluurr.quora.client

import com.bluurr.quora.domain.Answer
import com.bluurr.quora.domain.QuestionSearchResult
import java.io.Closeable

interface QuoraClient : Closeable {
    fun findQuestionsForTerm(term: String): Sequence<QuestionSearchResult>
    fun fetchAnswersForQuestionAt(href: String): Sequence<Answer>
}
