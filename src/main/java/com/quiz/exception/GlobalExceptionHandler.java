package com.quiz.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Catches Spring Boot automatic @Valid DTO failures (HTTP 400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // 2. Catches ResourceNotFoundException (HTTP 404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex , HttpServletRequest request) {
       ExceptionResponse response  = new ExceptionResponse(ex.getMessage() , false, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 3. Catches APIException (HTTP 400)
    @ExceptionHandler(APIException.class)
    public ResponseEntity<ExceptionResponse> handleAPIException(APIException ex , HttpServletRequest request) {
    	ExceptionResponse response  = new ExceptionResponse(ex.getMessage() , false, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 4. Catches UnauthorizedException (HTTP 401)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException ex , HttpServletRequest request) {
    	ExceptionResponse response  = new ExceptionResponse(ex.getMessage() , false, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // 5. Fallback safety net for unexpected system/code bugs (HTTP 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGlobalException(Exception ex , HttpServletRequest request) {
    	ExceptionResponse response  = new ExceptionResponse(ex.getMessage() , false, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
}