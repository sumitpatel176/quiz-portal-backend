package com.quiz.service;


import com.quiz.dto.request.QuizDto;
import com.quiz.dto.response.QuizResponse;
import com.quiz.entity.Quiz;
import java.util.List;

public interface QuizService {
    QuizResponse createQuiz(QuizDto quizDto);
    List<Quiz> getAllQuizzes();
    Quiz getQuizById(Long id);
    void deleteQuiz(Long id);
}
