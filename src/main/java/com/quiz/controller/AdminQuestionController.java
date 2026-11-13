package com.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.dto.request.QuestionDto;
import com.quiz.service.QuestionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/questions")
@PreAuthorize("hasRole('ADMIN')")
public class AdminQuestionController {

	@Autowired
    private QuestionService questionService;
	
	@PostMapping("/addQuestion/{quizId}")
    public ResponseEntity<QuestionDto> addQuestion(@PathVariable Long quizId, @Valid @RequestBody QuestionDto questionDto) {
        return ResponseEntity.ok(questionService.addQuestionToQuiz(quizId, questionDto));
    }
	

    @DeleteMapping("/deleteQuestion/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id){
    	questionService.deleteQuestion(id);
    	return ResponseEntity.ok("Question deleted successfully!");
    }
}
