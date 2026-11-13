package com.quiz.dto.response;

import java.time.LocalDateTime;

import com.quiz.entity.AttemptStatus;

public class QuizAttemptResponseDto {

    private Long attemptId;
    private Long userId;
    private Long quizId;
    private String quizTitle;
    private LocalDateTime startTime;
    private LocalDateTime submittedTime;
    private String status;
    private Integer score;
    private Integer tabSwitchCount;

    public QuizAttemptResponseDto() {
    }

    public QuizAttemptResponseDto(Long attemptId, Long userId, Long quizId, String quizTitle, LocalDateTime startTime, LocalDateTime submittedTime, String status, Integer score, Integer tabSwitchCount) {
        this.attemptId = attemptId;
        this.userId = userId;
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.startTime = startTime;
        this.submittedTime = submittedTime;
        this.status = status;
        this.score = score;
        this.tabSwitchCount = tabSwitchCount;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getSubmittedTime() {
        return submittedTime;
    }

    public void setSubmittedTime(LocalDateTime submittedTime) {
        this.submittedTime = submittedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTabSwitchCount() {
        return tabSwitchCount;
    }

    public void setTabSwitchCount(Integer tabSwitchCount) {
        this.tabSwitchCount = tabSwitchCount;
    }
}