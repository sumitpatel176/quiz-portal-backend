package com.quiz.service;



import com.quiz.dto.request.QuestionDto;
import com.quiz.dto.request.QuizDto;
import com.quiz.dto.response.QuizResponse;
import com.quiz.entity.Question;
import com.quiz.entity.Quiz;
import com.quiz.exception.ResourceNotFoundException;
import com.quiz.repository.QuizRepository;
import com.quiz.service.QuizService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImp implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    
    @Transactional
    @Override
    public QuizResponse createQuiz(QuizDto request) {
        
    	Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());

      
        Quiz savedQuiz = quizRepository.save(quiz);

       
        QuizResponse response = new QuizResponse();
        response.setId(savedQuiz.getId());
        response.setTitle(savedQuiz.getTitle());
        response.setDescription(savedQuiz.getDescription());
        
        return response;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));
    }

    @Transactional
    @Override
    public void deleteQuiz(Long id) {
    	
    	Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));
        quizRepository.deleteById(id);
    }

	
}