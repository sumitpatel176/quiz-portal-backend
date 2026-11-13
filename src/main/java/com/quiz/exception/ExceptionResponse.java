package com.quiz.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {

	private String message;
    private Boolean success;
    private LocalDateTime timestamp;
    private String details;
    
    public ExceptionResponse() {
        this.timestamp = LocalDateTime.now();
    }
    
    
	public ExceptionResponse(String message, Boolean success,  String details) {
		this.message = message;
		this.success = success;
		this.timestamp = LocalDateTime.now();
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
    
	
    
    
}
