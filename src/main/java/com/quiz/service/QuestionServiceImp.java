package com.quiz.service;
import com.quiz.dto.request.QuestionDto;
import com.quiz.entity.Question;
import com.quiz.entity.Quiz;
import com.quiz.exception.ResourceNotFoundException;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
	
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

    @Service
    public class QuestionServiceImp implements QuestionService {

        @Autowired
        private QuestionRepository questionRepository;

        @Autowired
        private QuizRepository quizRepository;

        @Transactional
        @Override
        public QuestionDto addQuestionToQuiz(Long quizId, QuestionDto questionDto) {
            
            Quiz quiz = quizRepository.findById(quizId)
                    .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));

            
            Question question = new Question();
            question.setQuestionText(questionDto.getQuestionText());
            question.setOptionA(questionDto.getOptionA());
            question.setOptionB(questionDto.getOptionB());
            question.setOptionC(questionDto.getOptionC());
            question.setOptionD(questionDto.getOptionD());
            question.setCorrectOption(questionDto.getCorrectOption());

            
            question.setQuiz(quiz);

            
            Question savedQuestion = questionRepository.save(question);

            
            return convertToDto(savedQuestion);
        }


        
        @Transactional
        @Override
        public QuestionDto updateQuestion(Long questionId, QuestionDto questionDto) {
            Question existingQuestion = questionRepository.findById(questionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));

            existingQuestion.setQuestionText(questionDto.getQuestionText());
            existingQuestion.setOptionA(questionDto.getOptionA());
            existingQuestion.setOptionB(questionDto.getOptionB());
            existingQuestion.setOptionC(questionDto.getOptionC());
            existingQuestion.setOptionD(questionDto.getOptionD());
            existingQuestion.setCorrectOption(questionDto.getCorrectOption());

            Question updatedQuestion = questionRepository.save(existingQuestion);
            return convertToDto(updatedQuestion);
        }

        
        @Transactional
        @Override
        public void deleteQuestion(Long questionId) {
            Question question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
            questionRepository.delete(question);
        }

       
        private QuestionDto convertToDto(Question question) {
            QuestionDto dto = new QuestionDto();
            dto.setId(question.getId());
            dto.setQuestionText(question.getQuestionText());
            dto.setOptionA(question.getOptionA());
            dto.setOptionB(question.getOptionB());
            dto.setOptionC(question.getOptionC());
            dto.setOptionD(question.getOptionD());
            dto.setCorrectOption(question.getCorrectOption());
            return dto;
        }


        @Transactional
        @Override
        public List<QuestionDto> getQuestionsByQuiz(Long quizId) {
            
            List<Question> questions = questionRepository.findByQuizId(quizId);

            
            List<QuestionDto> dtoList = new ArrayList<>();
            for (Question q : questions) {
                QuestionDto dto = new QuestionDto();
                BeanUtils.copyProperties(q, dto); 
                dtoList.add(dto);
            }

            return dtoList;
        }

    }
