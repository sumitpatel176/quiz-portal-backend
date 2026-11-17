package com.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.dto.request.LoginRequest;
import com.quiz.dto.request.RegisterRequest;
import com.quiz.dto.response.AuthResponse;
import com.quiz.dto.response.AuthResult;
import com.quiz.dto.response.UserResponse;
import com.quiz.entity.User;
import com.quiz.repository.UserRepository;
import com.quiz.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;
	

	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
	    UserResponse userResponse = userService.registerUser(request);
	    return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest request) {
		AuthResult authResult = userService.loginUser(request);

	    // 2. HttpOnly Cookie Build Karein
	    ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", authResult.getRefreshToken())
	            .httpOnly(true)                  // JavaScript access block (XSS Protection)
	            .secure(true)                   // Localhost testing ke liye false (Production HTTPS par true)
	            .path("/api/v1/auth/refresh")       // Specific endpoint path limit
	            .maxAge(7 * 24 * 60 * 60)        // 7 Days Expiry
	            .sameSite("None")              // CSRF Protection
	            .build();

	    // 3. Header mein Cookie + Body mein AuthResponse
	    return ResponseEntity.ok()
	            .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
	            .body(authResult.getAuthResponse());
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logoutUser(HttpServletRequest request) {
	    
	    // 1. Service Layer se Access Token blacklist karwaya
	    userService.logoutUser(request);

	    // 2. Refresh Token Cookie ko Expire/Delete karne ke liye maxAge(0) wali empty cookie banao
	    ResponseCookie cleanCookie = ResponseCookie.from("refreshToken", "")
	            .httpOnly(true)
	            .secure(true)                   // Localhost testing ke liye false (Production HTTPS par true)
	            .path("/api/v1/auth/refresh")       // Clean target path
	            .maxAge(0)                       // maxAge = 0 matlab Cookie Browser se turant DELETE ho jayegi
	            .sameSite("None")
	            .build();

	    // 3. Response Return kiya
	    return ResponseEntity.ok()
	            .header(HttpHeaders.SET_COOKIE, cleanCookie.toString())
	            .body("Logged out successfully!");
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
	    try {
	        // Service layer ko call kiya
	        AuthResult authResult = userService.refreshToken(refreshToken);

	        // Response Return karo
	        return ResponseEntity.ok(authResult.getAuthResponse());

	    } catch (Exception e) {
	        // Exception aane par Safe 401 Unauthorized do
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(e.getMessage());
	    }
	}
	
}
