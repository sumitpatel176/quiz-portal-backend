package com.quiz;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class QuizApplication {
	
	@PostConstruct
	public void intit() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
	}

	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);
	}

}
