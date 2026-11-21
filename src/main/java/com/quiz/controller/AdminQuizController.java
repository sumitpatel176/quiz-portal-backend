package com.quiz.controller;

import java.util.HashMap;
import java.util.Map;

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

import com.quiz.dto.request.QuizDto;
import com.quiz.dto.response.QuizResponse;
import com.quiz.entity.Quiz;
import com.quiz.service.QuizService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/quizzes")
@PreAuthorize("hasRole('ADMIN')")
public class AdminQuizController {

	@Autowired
	private QuizService quizService;
	
	@PostMapping("/createquiz")
	public ResponseEntity<QuizResponse> createQuiz(@Valid @RequestBody QuizDto quizDto){
		QuizResponse createquiz = quizService.createQuiz(quizDto);
		return ResponseEntity.ok(createquiz);
	}
	
	@DeleteMapping("/deleteQuiz/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok("Quiz deleted Successfully.");
    }
}
