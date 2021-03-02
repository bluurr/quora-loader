package com.bluurr.quora.controller

import com.bluurr.quora.domain.dto.QuestionResponse
import com.bluurr.quora.domain.dto.QuestionSearchResponse
import com.bluurr.quora.service.QuestionSearchService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("questions")
class QuestionController(val questionSearchService: QuestionSearchService) {

    @GetMapping
    fun findQuestionForTerm(@RequestParam term: String, @RequestParam(required = false, defaultValue = "5") limit: Int): List<QuestionSearchResponse> {

        return questionSearchService.findQuestionsForTerm(term, limit);
    }


    @GetMapping( "{id}" )
    fun getQuestion(@PathVariable id: UUID, @RequestParam(required = false, defaultValue = "5") answersLimit: Int): QuestionResponse {

         return questionSearchService.getQuestion(id, answersLimit)
    }
}
