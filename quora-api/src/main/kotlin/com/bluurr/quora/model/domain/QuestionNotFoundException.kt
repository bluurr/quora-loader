package com.bluurr.quora.model.domain

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason = "Question not found")
class QuestionNotFoundException : RuntimeException("Question not found")
