package com.quiz.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.service.UserService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/admin/user")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

	@Autowired
	private UserService userService;
	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<Map<String, String>> deleteUserByAdmin(@PathVariable Long userId) {
	    userService.deleteUser(userId);
	    
	    Map<String, String> response = new HashMap<>();
	    response.put("message", "User and associated results deleted successfully by Admin.");
	    
	    return ResponseEntity.ok(response);
	}
}
