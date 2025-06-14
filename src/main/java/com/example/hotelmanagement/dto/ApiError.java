package com.example.hotelmanagement.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Getter
public class ApiError {

    private String message;

    private List<String> errors;

    private HttpStatus status;
}
