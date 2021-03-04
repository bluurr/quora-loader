package com.bluurr.quora.controller

import io.restassured.RestAssured.given
import io.restassured.response.ValidatableResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class QuestionControllerIntegrationTest {

    @LocalServerPort
    var port: Int = 0

    @Nested
    inner class SearchForQuestion {

        @ValueSource(strings = ["Java", "Kotlin"])
        @ParameterizedTest
        fun `Give a search term When searching for a question Then return question search results`(term: String) {

            val searchResult = searchForQuestionsWithTerm(term)

            // Then

            val results = searchResult
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", TestQuestionSearchResult::class.java)


            assertThat(results).isNotEmpty

            assertThat(results).allSatisfy {

                assertThat(it.id).isNotNull
                assertThat(it.ask).isNotBlank
                assertThat(it.location).isNotBlank
            }
        }

        @Test
        fun `Give a blank search term When searching for a question Then return bad request`() {

            val searchResult = searchForQuestionsWithTerm("")

            // Then

            val results = searchResult
                .statusCode(400)
                .extract()
                .body()
                .jsonPath()
                .getString("message")


            assertThat(results).isEqualTo("findQuestionForTerm.term: must not be blank")
        }
    }

    @Nested
    inner class FetchAnswers {

        private val term = "Java"

        @ValueSource(ints = [ 1, 3, 5])
        @ParameterizedTest(name = "[{index}] answer limit {0}")
        fun `Give a question When fetching question with requested answer count Then return full question`(answerLimit: Int) {

            // Given

            val questionId = searchForQuestionsWithTerm(term)
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getUUID("[0].id")

            // When

            val question = fetchFullQuestion(questionId, answerLimit)

            // Then

            val result = question
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getObject(".", TestQuestionResult::class.java)


            assertThat(result.id).isEqualTo(questionId)
            assertThat(result.asked).isNotBlank
            assertThat(result.answers).hasSize(answerLimit)
        }
    }

    fun searchForQuestionsWithTerm(term: String, limit: Int = 1): ValidatableResponse {

        return given()
            .port(port)
            .contentType("application/json")
            .queryParam("term", term)
            .queryParam("limit", limit)
            .`when`()
            .get("questions")
            .then()
    }

    fun fetchFullQuestion(questionId: UUID, answerLimit: Int = 1): ValidatableResponse {

        return given()
            .port(port)
            .contentType("application/json")
            .queryParam("answersLimit", answerLimit)
            .`when`()
            .get("questions/{questionId}", questionId.toString())
            .then()
    }


}

data class TestQuestionSearchResult(val id: UUID, val ask: String, val location: String)

data class TestQuestionResult(val id: UUID, val asked: String, val answers: List<Any>)
