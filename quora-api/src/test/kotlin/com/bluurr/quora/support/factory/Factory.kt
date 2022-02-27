package com.bluurr.quora.support.factory

import com.bluurr.quora.model.dto.AnswerDto
import com.bluurr.quora.model.dto.QuestionSearchResponse
import java.util.*

object QuestionSearchResponseFactory {

    fun questionSearchResponse(
        id: UUID = UUID.randomUUID(),
        ask: String = "Java",
        location:String = "question/${ask}"
    ) = QuestionSearchResponse (
        id = id,
        ask = ask,
        location = location
    )

    fun questionAnswersDto(count: Int) = (1..count).map {
        AnswerDto(listOf("answer text"))
    }
}
