package com.quiz.service;

import com.quiz.dto.request.SubmitQuizRequest;
import com.quiz.dto.response.QuestionResponse;
import com.quiz.dto.response.ResultDto;
import com.quiz.entity.Question;
import com.quiz.entity.Quiz;
import com.quiz.entity.QuizAttempt;
import com.quiz.entity.Result;
import com.quiz.entity.User;
import com.quiz.exception.APIException;
import com.quiz.exception.ResourceNotFoundException;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.ResultRepository;
import com.quiz.repository.UserRepository;
import com.quiz.service.ResultService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultServiceImp implements ResultService {

	@Autowired
    private ResultRepository resultRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;
    
    
    @Override
    @Transactional
    public ResultDto saveAndBuildResult(QuizAttempt attempt, int score, int totalQuestions) {
        Result result = new Result();
        result.setQuizId(attempt.getQuiz().getId());
        result.setQuizTitle(attempt.getQuiz().getTitle());
        result.setScore(score);
        result.setTotalQuestions(totalQuestions);
        result.setCompletedAt(LocalDateTime.now());
        result.setUser(attempt.getUser());

        Result savedResult = resultRepository.save(result);

        return mapToDto(savedResult);
    }
    
    @Override
    public List<ResultDto> getUserResults(Long userId) {
        List<Result> results = resultRepository.findByUserId(userId);
        
        // Entity List -> DTO List Mapping
        return results.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Helper method for mapping (All-Args Constructor or Setters)
    private ResultDto mapToDto(Result result) {
        return new ResultDto(
            result.getId(),
            result.getQuizId(),
            result.getQuizTitle(),
            result.getScore(),
            result.getTotalQuestions(),
            result.getCompletedAt(),
            result.getUser().getId()
        );
    }
    
	@Override
	public List<Result> getLeaderboardByQuizId(Long quizId) {
		
		return resultRepository.findTop10ByQuizIdOrderByScoreDesc(quizId);
	}

	@Override
	public List<Result> findAllByQuizIdOrderByCompletedAtDesc(Long quizId) {
	
		return resultRepository.findAllByQuizIdOrderByCompletedAtDesc(quizId);
	}

	

}