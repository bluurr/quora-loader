package com.bluurr.quora.service

import com.bluurr.quora.client.QuoraClient
import com.bluurr.quora.client.provider.QuoraClientProvider
import com.bluurr.quora.model.Answer
import com.bluurr.quora.model.QuestionSearchResult
import com.bluurr.quora.model.dto.QuestionSearchResponse
import com.bluurr.quora.model.domain.QuestionNotFoundException
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.cache.Cache
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class QuestionSearchServiceTest {

    @Mock
    private lateinit var client: QuoraClient

    @Mock
    private lateinit var cache: Cache

    private lateinit var service: QuestionSearchService

    @BeforeEach
    fun prepare() {

        val clientProvider = TestDoubleQuoraClientProvider(client)

        service = QuestionSearchService(clientProvider, cache)
    }

    @Test
    fun `Given one search result When finding question for search term Then return 1 search result`() {

        // Given

        val term = "Testing"

        val searchResult = QuestionSearchResult.builder()
            .location("http://example.com/1")
            .title("Example Title")
            .build()

        whenever(client.findQuestionsForTerm(term)).thenReturn(sequence {
            yield(searchResult)
        })

        // When

        val result = service.findQuestionsForTerm(term, 1)

        // Then

        assertThat(result).hasSize(1)
        assertThat(result).allSatisfy {

            assertThat(it.id).isNotNull
            assertThat(it.ask).isEqualTo("Example Title")
            assertThat(it.location).isEqualTo("http://example.com/1")
        }
    }

    @Test
    fun `Given question exists When fetching full question Then return question`() {

        // Given

        val location = "https://www.example.com/question-here"

        whenever(client.getAnswersForQuestionAt(location)).thenReturn(sequence {

            val answer = Answer.builder()
                .answerBy("Bob")
                .paragraphs(listOf("Hello", "World"))
                .build()

            yield(answer)
        })

        val searchResult = QuestionSearchResponse(id = UUID.randomUUID(), ask = "What is Java", location)

        whenever(cache.get(searchResult.id, QuestionSearchResponse::class.java)).thenReturn(searchResult)

        // When

        val result = service.getQuestion(searchResult.id, 1)

        // Then

        assertThat(result).isNotNull
        assertThat(result.id).isEqualTo(searchResult.id)
        assertThat(result.answers).isNotEmpty
    }

    @Test
    fun `Given invalid question id When fetching full question Then throw not found exception`() {


        val error = assertThrows(QuestionNotFoundException::class.java) {

            val invalidQuestionId = UUID.randomUUID()

            service.getQuestion(invalidQuestionId, 1)
        }

        // Then

        assertThat(error).hasMessage("Question not found")
    }

}

class TestDoubleQuoraClientProvider(private val client:QuoraClient): QuoraClientProvider {

    override fun <T> client(block: (QuoraClient) -> T): T {
        return block(client)
    }
}
