package com.bluurr.quora.controller

import com.bluurr.quora.model.dto.QuestionResponse
import com.bluurr.quora.model.dto.QuestionSearchResponse
import com.bluurr.quora.service.QuestionSearchService
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.constraints.NotBlank

@Validated
@RestController
@RequestMapping(path=[ "questions" ], produces = [ APPLICATION_JSON_VALUE ])
class QuestionController(val questionSearchService: QuestionSearchService) {

    @GetMapping
    fun findQuestionForTerm(@RequestParam @NotBlank term: String, @RequestParam(required = false, defaultValue = "5") limit: Int): List<QuestionSearchResponse> {

        return questionSearchService.findQuestionsForTerm(term, limit);
    }


    @GetMapping( "{id}" )
    fun getQuestion(@PathVariable id: UUID, @RequestParam(required = false, defaultValue = "5") answersLimit: Int): QuestionResponse {

         return questionSearchService.getQuestion(id, answersLimit)
    }
}
