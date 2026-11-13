package com.quiz.controller;

import com.quiz.entity.QuizAttempt;
import com.quiz.dto.request.SubmitQuizRequest;
import com.quiz.dto.response.QuizAttemptResponseDto;
import com.quiz.dto.response.ResultDto;
import com.quiz.service.QuizEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attempts")
public class QuizAttemptController {

    private final QuizEvaluationService evaluationService;

    @Autowired
    public QuizAttemptController(QuizEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    // 1. Start Exam Session
    @PostMapping("/start/{userId}/{quizId}")
    public ResponseEntity<QuizAttemptResponseDto> startQuiz(
    		@PathVariable Long userId,
            @PathVariable Long quizId) {
        QuizAttemptResponseDto attempt = evaluationService.startQuizAttempt(userId, quizId);
        return ResponseEntity.ok(attempt);
    }

    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<ResultDto> submitQuiz(
            @PathVariable Long attemptId,
            @RequestBody(required = false) SubmitQuizRequest request) {
        ResultDto result = evaluationService.validateAndSubmitQuiz(attemptId, request);
        return ResponseEntity.ok(result);
    }

    // 3. Log Tab Switch Violation
    @PostMapping("/{attemptId}/violation")
    public ResponseEntity<Integer> registerViolation(@PathVariable Long attemptId) {
        int totalViolations = evaluationService.logTabSwitchViolation(attemptId);
        return ResponseEntity.ok(totalViolations);
    }
}