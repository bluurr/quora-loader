package com.bluurr.quora.domain.dto

import java.util.*

data class QuestionSearchResponse(val id: UUID, val ask: String, val location: String) {
}
