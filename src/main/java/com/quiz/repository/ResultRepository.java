package com.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.entity.Result;
import com.quiz.entity.User;

import jakarta.transaction.Transactional;
@Repository
public interface ResultRepository extends JpaRepository<Result,Long> {

	

	boolean existsByUserIdAndQuizId(Long userId, Long quizId);
	List<Result>findByUserId(Long UserId);
	List<Result> findTop10ByQuizIdOrderByScoreDesc(Long quizId);
	List<Result> findAllByQuizIdOrderByCompletedAtDesc(Long quizId);
	
}
