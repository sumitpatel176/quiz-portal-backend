package com.quiz.dto.response;

import javax.swing.Spring;

import org.springframework.beans.factory.annotation.Autowired;

public class AuthResult {
	

	private AuthResponse authResponse;
	private String refreshToken;
	
	
	public AuthResult() {
		
	} 
	public AuthResult(AuthResponse authResponse, String refreshToken) {
		this.authResponse = authResponse;
		this.refreshToken = refreshToken;
	}
	public AuthResponse getAuthResponse() {
		return authResponse;
	}
	public void setAuthResponse(AuthResponse authResponse) {
		this.authResponse = authResponse;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	} 
	
	
	

}
