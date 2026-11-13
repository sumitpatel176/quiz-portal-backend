package com.quiz.service;

import com.quiz.dto.request.SubmitQuizRequest;
import com.quiz.dto.response.QuizAttemptResponseDto;
import com.quiz.dto.response.ResultDto;


public interface QuizEvaluationService {

	QuizAttemptResponseDto startQuizAttempt(Long userId, Long quizId);
	ResultDto validateAndSubmitQuiz(Long attemptId, SubmitQuizRequest request);
	int logTabSwitchViolation(Long attemptId);
	
}
