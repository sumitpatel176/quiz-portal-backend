package com.quiz.dto.response;

import java.util.List;

public class QuizResponse {

    private Long id;
    private String title;
    private String description;
    private List<QuestionResponse> questions;

    // Constructors
    public QuizResponse() {}

    public QuizResponse(Long id, String title, String description, List<QuestionResponse> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<QuestionResponse> getQuestions() { return questions; }
    public void setQuestions(List<QuestionResponse> questions) { this.questions = questions; }
}