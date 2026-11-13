package com.quiz.service;

import com.quiz.entity.*;
import com.quiz.dto.request.SubmitQuizRequest;
import com.quiz.dto.response.QuestionResponse;
import com.quiz.dto.response.QuizAttemptResponseDto;
import com.quiz.dto.response.ResultDto;
import com.quiz.repository.*;
import com.quiz.exception.ResourceNotFoundException;
import com.quiz.exception.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class QuizEvaluationServiceImp implements QuizEvaluationService{

    private final QuizAttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final ResultService resultService; // Dependency on ResultService interface

    @Autowired
    public QuizEvaluationServiceImp(QuizAttemptRepository attemptRepository,
                                 UserRepository userRepository,
                                 QuizRepository quizRepository,
                                 QuestionRepository questionRepository,
                                 ResultService resultService) {
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.resultService = resultService;
    }

    // 1. Initialize Live Quiz Session Attempt
    @Transactional
    public QuizAttemptResponseDto startQuizAttempt(Long userId, Long quizId) {
    	Optional<QuizAttempt> existingAttempt = attemptRepository.findFirstByUserIdAndQuizIdAndStatusOrderByStartTimeDesc(userId, quizId, AttemptStatus.IN_PROGRESS);

        QuizAttempt attempt;
        if (existingAttempt.isPresent()) {
            attempt = existingAttempt.get();
        } else {
        	
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with ID: " + quizId));

        attempt = new QuizAttempt();
        attempt.setUser(user);
        attempt.setQuiz(quiz);
        attempt.setStartTime(LocalDateTime.now());
        attempt.setStatus(AttemptStatus.IN_PROGRESS);
        attempt.setTabSwitchCount(0);

        attempt = attemptRepository.save(attempt);
        }
        
        QuizAttemptResponseDto dto = new QuizAttemptResponseDto();
        dto.setAttemptId(attempt.getId());
        dto.setUserId(attempt.getUser().getId());
        dto.setQuizId(attempt.getQuiz().getId());
        dto.setQuizTitle(attempt.getQuiz().getTitle());
        dto.setStartTime(attempt.getStartTime());
        dto.setSubmittedTime(attempt.getSubmittedTime());
        dto.setStatus(attempt.getStatus().name());
        dto.setScore(attempt.getScore());
        dto.setTabSwitchCount(attempt.getTabSwitchCount());

        return dto;
    }

    
    @Transactional
    public ResultDto validateAndSubmitQuiz(Long attemptId, SubmitQuizRequest request) {
        QuizAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("Attempt not found with ID: " + attemptId));

        if (attempt.getStatus() != AttemptStatus.IN_PROGRESS) {
            throw new APIException("Quiz is already submitted or expired!");
        }

        LocalDateTime now = LocalDateTime.now();
        Quiz quiz = attempt.getQuiz();

        // Server-Side Elapsed Time Validation
        long secondsElapsed = ChronoUnit.SECONDS.between(attempt.getStartTime(), now);
        long allowedSeconds = 30 * 60L + 5; // 5-second network grace period
        boolean isExpired = secondsElapsed > allowedSeconds;

        int totalQuestions = quiz.getQuestions() != null ? quiz.getQuestions().size() : 0;
        int score = 0;

        List<QuestionResponse> responses = request != null ? request.getResponses() : null;

        // Answer Matching Logic
        if (responses != null) {
            for (QuestionResponse resp : responses) {
                if (resp.getQuestionId() != null && resp.getSelectedOption() != null && !resp.getSelectedOption().trim().isEmpty()) {
                    Question question = questionRepository.findById(resp.getQuestionId())
                            .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + resp.getQuestionId()));

                    if (question.getCorrectOption() != null && question.getCorrectOption().trim().equalsIgnoreCase(resp.getSelectedOption().trim())) {
                        score++;
                    }
                    
                }
            }
        }

        // Update Live Session Attempt Status
        attempt.setScore(score);
        attempt.setStatus(isExpired ? AttemptStatus.EXPIRED : AttemptStatus.SUBMITTED);
        attempt.setSubmittedTime(now);
        attemptRepository.save(attempt);

        return resultService.saveAndBuildResult(attempt, score, totalQuestions);
    }

    // 3. Log Tab Switch Violations
    @Transactional
    public int logTabSwitchViolation(Long attemptId) {
        QuizAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("Attempt not found with ID: " + attemptId));

        int currentCount = attempt.getTabSwitchCount() == null ? 0 : attempt.getTabSwitchCount();
        attempt.setTabSwitchCount(currentCount + 1);
        attemptRepository.save(attempt);

        return attempt.getTabSwitchCount();
    }
}