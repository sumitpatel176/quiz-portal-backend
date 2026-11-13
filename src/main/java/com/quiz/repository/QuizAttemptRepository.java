package com.quiz.repository;

import com.quiz.entity.QuizAttempt;
import com.quiz.entity.AttemptStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

	Optional<QuizAttempt> findFirstByUserIdAndQuizIdAndStatusOrderByStartTimeDesc(
	        Long userId, Long quizId, AttemptStatus status);

    List<QuizAttempt> findByUserIdOrderByStartTimeDesc(Long userId);
}