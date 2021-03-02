package com.bluurr.quora.service

import com.bluurr.quora.client.QuoraClient
import com.bluurr.quora.domain.Answer
import com.bluurr.quora.domain.QuestionSearchResult
import com.bluurr.quora.domain.dto.AnswerDto
import com.bluurr.quora.domain.dto.QuestionResponse
import com.bluurr.quora.domain.dto.QuestionSearchResponse
import com.bluurr.quora.domain.model.QuestionNotFoundException
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service
import java.util.*

@Service
class QuestionSearchService(val quoraClient: QuoraClient, cacheManager: CacheManager) {

    private val cache: Cache = cacheManager.getCache("questions") ?: throw RuntimeException("Cache not found")

    fun getQuestion(id: UUID): QuestionResponse{

        val question = cache.get(id, QuestionSearchResponse::class.java) ?: throw QuestionNotFoundException()

        val pageAnswers = quoraClient.fetchAnswersForQuestionAt(question.location)

        val answers = pageAnswers
            .take(1)
            .map {
                mapToAnswerDto(it)
            }
            .toList()


        return QuestionResponse(question.id, question.ask, answers)
    }

    fun findQuestionsForTerm(term: String, limit: Int): List<QuestionSearchResponse> {

        val questions = quoraClient.fetchQuestionsForTerm(term)

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


fun mapToQuestionSearchResponse(searchResult: QuestionSearchResult): QuestionSearchResponse {

    return QuestionSearchResponse(UUID.randomUUID(), searchResult.title, searchResult.location)
}

fun mapToAnswerDto(answer: Answer): AnswerDto {

    return AnswerDto(answer.paragraphs)
}





