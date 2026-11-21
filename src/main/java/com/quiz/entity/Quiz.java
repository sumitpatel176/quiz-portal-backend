package com.quiz.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;

@Entity
@Table(name="quizzes")
public class Quiz {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
@Column
private String title;
@Column
private String description;


@OneToMany(mappedBy = "quiz", cascade =CascadeType.ALL,fetch=FetchType.LAZY)
private List<Question> questions;

@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
private List<QuizAttempt> attempts;

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public List<Question> getQuestions() {
	return questions;
}
public void setQuestions(List<Question> questions) {
	this.questions = questions;
}
}
