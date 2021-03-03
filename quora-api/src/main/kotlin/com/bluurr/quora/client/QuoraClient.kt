package com.bluurr.quora.client

import com.bluurr.quora.model.Answer
import com.bluurr.quora.model.QuestionSearchResult
import java.io.Closeable

interface QuoraClient : Closeable {
    fun findQuestionsForTerm(term: String): Sequence<QuestionSearchResult>
    fun getAnswersForQuestionAt(href: String): Sequence<Answer>
}
