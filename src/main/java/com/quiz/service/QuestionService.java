package com.quiz.service;


import com.quiz.dto.request.QuestionDto;
import com.quiz.entity.Question;
import java.util.List;

public interface QuestionService {
    QuestionDto addQuestionToQuiz(Long quizId, QuestionDto questionDto);
    List<QuestionDto> getQuestionsByQuiz(Long quizId);
    QuestionDto updateQuestion(Long questionId, QuestionDto questionDto);
    void deleteQuestion(Long id);
}