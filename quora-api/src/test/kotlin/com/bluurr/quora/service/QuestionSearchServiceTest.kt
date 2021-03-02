package com.bluurr.quora.service

import com.bluurr.quora.client.QuoraClient
import com.bluurr.quora.domain.QuestionSearchResult
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.cache.Cache

@ExtendWith(MockitoExtension::class)
internal class QuestionSearchServiceTest {

    @Mock
    lateinit var client: QuoraClient

    @Mock
    lateinit var cache: Cache

    @InjectMocks
    lateinit var service: QuestionSearchService

    @Test
    fun `given search has 1 result when find question for term then return 1 search result`() {

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

}
