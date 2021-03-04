package com.bluurr.quora.service

import com.bluurr.quora.client.provider.QuoraClientProvider
import com.bluurr.quora.model.Answer
import com.bluurr.quora.model.QuestionSearchResult
import com.bluurr.quora.model.dto.AnswerDto
import com.bluurr.quora.model.dto.QuestionResponse
import com.bluurr.quora.model.dto.QuestionSearchResponse
import com.bluurr.quora.model.domain.QuestionNotFoundException
import org.springframework.cache.Cache
import java.util.*

class QuestionSearchService(private val clientProvider: QuoraClientProvider, private val cache: Cache) {

    fun getQuestion(id: UUID, answersLimit: Int): QuestionResponse {

        val question = cache.get(id, QuestionSearchResponse::class.java) ?: throw QuestionNotFoundException()

        return clientProvider.client { client ->

            val answers = client.getAnswersForQuestionAt(question.location)

            val answerResults = answers
                .take(answersLimit)
                .map {
                    mapToAnswerDto(it)
                }
                .toList()

            QuestionResponse(question.id, question.ask, answerResults)
        }
    }

    fun findQuestionsForTerm(term: String, limit: Int): List<QuestionSearchResponse> {

        val results = clientProvider.client { client ->

            val questions = client.findQuestionsForTerm(term)

            val results = questions
                .take(limit)
                .map {
                    mapToQuestionSearchResponse(it)
                }
                .toList()

            results
        }

        // Update cache
        results.forEach {
            cache.put(it.id, it)
        }

        return results
    }
}


fun mapToQuestionSearchResponse(searchResult: QuestionSearchResult): QuestionSearchResponse {

    return QuestionSearchResponse(UUID.randomUUID(), searchResult.title, searchResult.location)
}

fun mapToAnswerDto(answer: Answer): AnswerDto {

    return AnswerDto(answer.paragraphs)
}
