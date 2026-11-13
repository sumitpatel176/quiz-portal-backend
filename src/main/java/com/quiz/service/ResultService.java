package com.quiz.service;

import com.quiz.dto.request.SubmitQuizRequest;
import com.quiz.dto.response.ResultDto;
import com.quiz.entity.Quiz;
import com.quiz.entity.QuizAttempt;
import com.quiz.entity.Result;
import com.quiz.entity.User;

import java.util.List;

public interface ResultService {
	ResultDto saveAndBuildResult(QuizAttempt attempt, int score, int totalQuestions);
    List<ResultDto> getUserResults(Long userId);
    List<Result> getLeaderboardByQuizId(Long quizId);
    List<Result>findAllByQuizIdOrderByCompletedAtDesc(Long quizId);
    
    
    
}