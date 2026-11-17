package com.quiz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quiz.config.UserDetailServiceImpl;
import com.quiz.dto.request.LoginRequest;
import com.quiz.dto.request.RegisterRequest;
import com.quiz.dto.response.AuthResponse;
import com.quiz.dto.response.AuthResult;
import com.quiz.dto.response.UserResponse;
import com.quiz.entity.User;
import com.quiz.exception.APIException;
import com.quiz.exception.ResourceNotFoundException;
import com.quiz.repository.ResultRepository;
import com.quiz.repository.UserRepository;
import com.quiz.security.JwtProvider;
import com.quiz.security.TokenBlacklistService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	private UserRepository ur;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ResultRepository resultRepository;
	@Autowired
	private TokenBlacklistService tokenBlacklistService;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

	@Override
	public Boolean existsByEmail(String email) {
		return ur.existsByEmail(email);
	}


	@Override
	@Transactional
	public UserResponse registerUser(RegisterRequest request) {
		if (ur.existsByEmail(request.getEmail())) {
			throw new APIException("Email is already taken!");
        }
		
		User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        
        user.setRole("user");
        
        
        User savedUser = ur.save(user);
        
        
		return new UserResponse("User Register Successfully ", savedUser.getId(), savedUser.getUsername(),savedUser.getEmail(),savedUser.getRole());
	}

	@Override
	public Optional<User> findByUsername(String username) {
		
		return ur.findByUsername(username);
	}
	
	

	
	
	 public AuthResult loginUser(LoginRequest loginRequest) {

        // DaoAuthenticationProvider internally BCrypt Match karega
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // JWT Token Generate karke AuthR0esponse DTO me wrap karke bhejenge
        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);
        
        User user = ur.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loginRequest.getEmail()));
        
        
        UserResponse userResponse = new UserResponse();
        userResponse.setMessage("User login success....");
	    userResponse.setId(user.getId());
	    userResponse.setUsername(user.getUsername());
	    userResponse.setEmail(user.getEmail());
	    userResponse.setRole(user.getRole().toUpperCase());
        
        AuthResponse authresponse =  new AuthResponse("user login successfully",accessToken, userResponse);
        
        return new AuthResult (authresponse , refreshToken);
    }


	 @Override
	 public void logoutUser(HttpServletRequest request) {
		    
		    String authHeader = request.getHeader("Authorization");
		    
		    if (authHeader != null && authHeader.startsWith("Bearer ")) {
		        String accessToken = authHeader.substring(7);
		        
		       
		        tokenBlacklistService.blacklistToken(accessToken);
		    }
		}
	 
	 public AuthResult refreshToken(String refreshToken) {
		    
		    if (refreshToken == null || refreshToken.isBlank() || !jwtProvider.validateToken(refreshToken)) {
		        throw new RuntimeException("Invalid or missing Refresh Token!");
		    }

		    
		    String email = jwtProvider.getEmailFromJwt(refreshToken);
		    UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(email);

		    UsernamePasswordAuthenticationToken authentication = 
		            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		    
		    String newAccessToken = jwtProvider.generateAccessToken(authentication);
		    
		    
		    
		    User user = ur.findByEmail(email)
		            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
		    
		    UserResponse userResponse = new UserResponse();
		    userResponse.setMessage("successfull token refresh....");
		    userResponse.setId(user.getId());
		    userResponse.setUsername(user.getUsername());
		    userResponse.setEmail(user.getEmail());
		    userResponse.setRole(user.getRole().toUpperCase());

		    // 4. AuthResponse DTO bana kar return karo
		    AuthResponse authResponse = new AuthResponse("Token Refreshed!", newAccessToken, userResponse);
		    
		    return new AuthResult(authResponse, refreshToken);
		}
	 
	@Override
	@Transactional
	public void deleteUser(Long userId) {
		
		User user = ur.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
		
		    ur.delete(user);
		}


	@Override
	public UserResponse getUserProfile(String email) {
		User user = ur.findByEmail(email)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
		
		UserResponse userResponse = new UserResponse();
		userResponse.setMessage("user -> "+ user.getUsername());
	    userResponse.setId(user.getId());
	    userResponse.setUsername(user.getUsername());
	    userResponse.setEmail(user.getEmail());
	    userResponse.setRole(user.getRole().toUpperCase());
		return userResponse;
	}

		
	}


