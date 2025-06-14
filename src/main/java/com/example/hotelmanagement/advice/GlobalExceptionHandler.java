package com.example.hotelmanagement.advice;

import com.example.hotelmanagement.dto.ApiError;
import com.example.hotelmanagement.dto.ApiResponse;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return buildResponseEntity(ApiError.builder()
                .message(e.getMessage())
                .errors(List.of())
                .status(HttpStatus.NOT_FOUND)
                .build());
    }

    private ResponseEntity<ApiResponse<?>> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }
}
