package com.quiz.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quiz.dto.request.LoginRequest;
import com.quiz.dto.request.RegisterRequest;
import com.quiz.dto.response.AuthResponse;
import com.quiz.entity.User;
import com.quiz.exception.ResourceNotFoundException;
import com.quiz.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/me")
	public ResponseEntity<?> getMyProfile(Authentication authentication) {
	    String currentUsername = authentication.getName();
	    return ResponseEntity.ok(userService.getUserProfile(currentUsername));
	}
}
