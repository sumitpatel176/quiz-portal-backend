package com.quiz.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {

	private String message;
    private String accessToken;
    private String tokenType;
    private UserResponse userResponse;

    // 1. Default No-Args Constructor (Jackson JSON parsing ke liye MUST hai)
    public AuthResponse() {
    }

    
   
    public AuthResponse(String message ,String accessToken, UserResponse userResponse) {
    	this.message = message;
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
        this.userResponse = userResponse;
    }

    

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	// Getters & Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }



	public UserResponse getUserResponse() {
		return userResponse;
	}



	public void setUserResponse(UserResponse userResponse) {
		this.userResponse = userResponse;
	}
    
    
    
}