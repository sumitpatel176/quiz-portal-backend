package com.quiz.controller;

import java.util.List;
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

import com.quiz.dto.request.SubmitQuizRequest;
import com.quiz.dto.response.ResultDto;
import com.quiz.entity.Result;
import com.quiz.entity.User;
import com.quiz.exception.ResourceNotFoundException;
import com.quiz.repository.UserRepository;
import com.quiz.service.ResultService;
import com.quiz.service.UserService;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
	public ResponseEntity<?> getMyResult(Authentication authentication) {
	    String currentUsername = authentication.getName();
	    User user = userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUsername));

      
        List<ResultDto> userResults = resultService.getUserResults(user.getId());

        return ResponseEntity.ok(userResults);
	}

    
    

    @GetMapping("/leaderboard/{quizId}")
    public ResponseEntity<List<Result>> getLeaderboardByQuizId(@PathVariable Long quizId) {
        return ResponseEntity.ok(resultService.getLeaderboardByQuizId(quizId));
    }
}