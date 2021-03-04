package com.bluurr.quora.controller

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletResponse
import javax.validation.ValidationException

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(e: ValidationException, response: HttpServletResponse) {

        // Overwrite the default 500 server exception to a 400 client exception.

        response.sendError(BAD_REQUEST.value())
    }

}
