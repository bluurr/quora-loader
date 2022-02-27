package com.bluurr.quora.service

import com.bluurr.quora.client.provider.QuoraClientProvider
import com.bluurr.quora.model.Answer
import com.bluurr.quora.model.QuestionSearchResult
import com.bluurr.quora.model.domain.QuestionNotFoundException
import com.bluurr.quora.model.dto.AnswerDto
import com.bluurr.quora.model.dto.QuestionResponse
import com.bluurr.quora.model.dto.QuestionSearchResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.Cache
import org.springframework.stereotype.Service
import java.util.*

@Service
class QuestionSearchService(private val clientProvider: QuoraClientProvider, @Qualifier("questionCache") private val cache: Cache) {

    fun getQuestion(id: UUID, answersLimit: Int): QuestionResponse {

        val question = cache.get(id, QuestionSearchResponse::class.java) ?: throw QuestionNotFoundException()

        return clientProvider.client { client ->

            val answers = client.getAnswersForQuestionAt(question.location)

            val answerResults = answers
                .take(answersLimit)
                .map(::mapToAnswerDto)
                .toList()

            QuestionResponse(question.id, question.ask, answerResults)
        }
    }

    fun findQuestionsForTerm(term: String, limit: Int): List<QuestionSearchResponse> {

        return clientProvider.client { client ->

            val questions = client.findQuestionsForTerm(term)

            questions
                .take(limit)
                .map(::mapToQuestionSearchResponse)
                .onEach { cache.put(it.id, it) }
                .toList()
        }
    }
}


fun mapToQuestionSearchResponse(searchResult: QuestionSearchResult) = QuestionSearchResponse(UUID.randomUUID(), searchResult.title, searchResult.location)
fun mapToAnswerDto(answer: Answer) = AnswerDto(answer.paragraphs)
