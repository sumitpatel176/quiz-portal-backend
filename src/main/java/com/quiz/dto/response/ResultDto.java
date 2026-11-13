package com.quiz.dto.response;

import java.time.LocalDateTime;

//ResultDto.java (Simple Class - No Database Annotations)
public class ResultDto {
 private Long id;
 private Long quizId;
 private String quizTitle;
 private int score;
 private int totalQuestions;
 private LocalDateTime completedAt;
 private Long userId; 
 
 
 public ResultDto() {
 }
 
 public ResultDto(Long id, Long quizId, String quizTitle, int score, int totalQuestions, LocalDateTime completedAt, Long userId) {
     this.id = id;
     this.quizId = quizId;
     this.quizTitle = quizTitle;
     this.score = score;
     this.totalQuestions = totalQuestions;
     this.completedAt = completedAt;
     this.userId = userId;
 }
 
 
 public Long getId() {
	return id;
 }
 public void setId(Long id) {
	this.id = id;
 }
 public Long getQuizId() {
	return quizId;
 }
 public void setQuizId(Long quizId) {
	this.quizId = quizId;
 }
 public String getQuizTitle() {
	return quizTitle;
 }
 public void setQuizTitle(String quizTitle) {
	this.quizTitle = quizTitle;
 }
 public int getScore() {
	return score;
 }
 public void setScore(int score) {
	this.score = score;
 }
 public int getTotalQuestions() {
	return totalQuestions;
 }
 public void setTotalQuestions(int totalQuestions) {
	this.totalQuestions = totalQuestions;
 }
 public LocalDateTime getCompletedAt() {
	return completedAt;
 }
 public void setCompletedAt(LocalDateTime completedAt) {
	this.completedAt = completedAt;
 }
 public Long getUserId() {
	return userId;
 }
 public void setUserId(Long userId) {
	this.userId = userId;
 }

 // Getters & Setters
 
}