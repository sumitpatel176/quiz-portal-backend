package com.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.entity.Result;
import com.quiz.service.ResultService;


@RestController
@RequestMapping("/api/admin/results")
@PreAuthorize("hasRole('ADMIN')")
public class AdminResultController {

	@Autowired
    private ResultService resultService;
	
	@GetMapping("/all/{quizId}")
    public ResponseEntity<List<Result>> getAllUserResults(@PathVariable Long quizId) {
        List<Result> results = resultService.findAllByQuizIdOrderByCompletedAtDesc(quizId);
        return ResponseEntity.ok(results);
    }
}
 