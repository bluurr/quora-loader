package com.bluurr.quora.service

import com.bluurr.quora.client.provider.QuoraClientProvider
import com.bluurr.quora.domain.Answer
import com.bluurr.quora.domain.QuestionSearchResult
import com.bluurr.quora.domain.dto.AnswerDto
import com.bluurr.quora.domain.dto.QuestionResponse
import com.bluurr.quora.domain.dto.QuestionSearchResponse
import com.bluurr.quora.domain.model.QuestionNotFoundException
import org.springframework.cache.Cache
import java.util.*

class QuestionSearchService(private val clientProvider: QuoraClientProvider, private val cache: Cache) {

    fun getQuestion(id: UUID, answersLimit: Int): QuestionResponse{

        val question = cache.get(id, QuestionSearchResponse::class.java) ?: throw QuestionNotFoundException()

        val quoraClient = clientProvider.get()

        quoraClient.use {

            val answers = quoraClient.getAnswersForQuestionAt(question.location)

            val answerResults = answers
                .take(answersLimit)
                .map {
                    mapToAnswerDto(it)
                }
                .toList()

            return QuestionResponse(question.id, question.ask, answerResults)
        }
    }

    fun findQuestionsForTerm(term: String, limit: Int): List<QuestionSearchResponse> {

        val quoraClient = clientProvider.get()

        quoraClient.use {

            val questions = quoraClient.findQuestionsForTerm(term)

            val results = questions
                .take(limit)
                .map {
                    mapToQuestionSearchResponse(it)
                }
                .toList()


            // Update cache
            results.forEach {
                cache.put(it.id, it)
            }

            return results
        }
    }
}


fun mapToQuestionSearchResponse(searchResult: QuestionSearchResult): QuestionSearchResponse {

    return QuestionSearchResponse(UUID.randomUUID(), searchResult.title, searchResult.location)
}

fun mapToAnswerDto(answer: Answer): AnswerDto {

    return AnswerDto(answer.paragraphs)
}
