package com.quiz.service;

import java.util.Optional;

import com.quiz.dto.request.LoginRequest;
import com.quiz.dto.request.RegisterRequest;
import com.quiz.dto.response.AuthResponse;
import com.quiz.dto.response.AuthResult;
import com.quiz.dto.response.UserResponse;
import com.quiz.entity.User;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	UserResponse registerUser(RegisterRequest request);
	
    AuthResult loginUser(LoginRequest request);
    
    void logoutUser(HttpServletRequest request);
    
    AuthResult refreshToken(String refreshToken);
    
    UserResponse getUserProfile(String email);
    Optional<User> findByUsername(String username);
    Boolean existsByEmail(String email);
    void deleteUser(Long userId);
}
