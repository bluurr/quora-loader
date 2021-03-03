package com.bluurr.quora.controller

import io.restassured.RestAssured.given
import io.restassured.response.ValidatableResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class QuestionControllerIntegrationTest {

    @LocalServerPort
    var port: Int = 0

    @ValueSource(strings = ["Java", "Kotlin"])
    @ParameterizedTest
    fun `Give a search term When search for question Then return question json` (term: String) {

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
}

data class TestQuestionSearchResult(val id: UUID, val ask: String, val location: String)
