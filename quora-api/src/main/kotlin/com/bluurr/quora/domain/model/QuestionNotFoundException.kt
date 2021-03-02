package com.bluurr.quora.domain.model

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason = "Question not found")
class QuestionNotFoundException : RuntimeException("Question not found")
