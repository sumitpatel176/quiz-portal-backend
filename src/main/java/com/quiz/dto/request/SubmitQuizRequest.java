package com.quiz.dto.request;

import java.util.List;

import com.quiz.dto.response.QuestionResponse;

public class SubmitQuizRequest {
    private Long userId;
    private Long quizId;
    private List<QuestionResponse> responses;

    public SubmitQuizRequest() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }

    public List<QuestionResponse> getResponses() { return responses; }
    public void setResponses(List<QuestionResponse> responses) { this.responses = responses; }
}