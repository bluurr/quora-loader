package com.bluurr.quora.controller

import com.bluurr.quora.model.dto.QuestionResponse
import com.bluurr.quora.service.QuestionSearchService
import com.bluurr.quora.support.factory.QuestionSearchResponseFactory.questionAnswersDto
import com.bluurr.quora.support.factory.QuestionSearchResponseFactory.questionSearchResponse
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(QuestionController::class)
internal class QuestionControllerIntegrationTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var questionService: QuestionSearchService

    @Nested
    inner class SearchForQuestionIntegrationTest {

        @ValueSource(strings = ["Java", "Kotlin"])
        @ParameterizedTest
        fun `Give a search ask When searching for a question Then return question search results`(ask: String) {

            // Given
            val response = questionSearchResponse(ask = ask)

            whenever(questionService.findQuestionsForTerm(ask, 1))
                .thenReturn(listOf(response))

            // When
            val result = mvc.get("/questions") {
                contentType = APPLICATION_JSON
                param("term", ask)
                param("limit", 1.toString())
            }

            // Then
            result.andExpect {
                status { isOk() }
                content {
                    json(
                        """
                            [
                                {
                                    "id":"${response.id}",
                                    "ask":"$ask",
                                    "location":"question/$ask"
                                }
                            ]
                        """.trimIndent()
                    )
                }
            }
        }
    }

    @Nested
    inner class FetchAnswersIntegrationTest {

        @ValueSource(ints = [1, 3, 5])
        @ParameterizedTest(name = "[{index}] answer limit {0}")
        fun `Give a question When fetching question with requested answer count Then return full question`(
            answerLimit: Int
        ) {

            // Given
            val question = questionSearchResponse()
            val answers = questionAnswersDto(answerLimit)

            // When
            whenever(questionService.getQuestion(question.id, answerLimit)).thenReturn(
                QuestionResponse(
                    id = question.id,
                    asked = question.ask,
                    answers = answers
                )
            )

            val result = mvc.get("/questions/{questionId}", question.id) {
                contentType = APPLICATION_JSON
                param("answersLimit", answerLimit.toString())
            }

            // Then
            result.andExpect {
                status { isOk() }
                content {
                    jsonPath("$.answers.length()") {
                        value(answerLimit)
                    }
                }
            }
        }
    }
}
