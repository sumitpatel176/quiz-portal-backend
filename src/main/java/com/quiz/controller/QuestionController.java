package com.quiz.controller;

import com.quiz.dto.request.QuestionDto;
import com.quiz.entity.Question;
import com.quiz.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

	@Autowired
    private QuestionService questionService;

    

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(questionService.getQuestionsByQuiz(quizId));
    }
    
}