package com.bluurr.quora.model.dto

import java.util.*

data class QuestionResponse(val id: UUID, val asked: String, val answers: List<AnswerDto>)
