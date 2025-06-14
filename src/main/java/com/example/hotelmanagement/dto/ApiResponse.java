package com.example.hotelmanagement.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {

    @JsonFormat(pattern = "dd-MM-YYYY hh:mm:ss a")
    private final LocalDateTime timestamp;

    private final T data;

    private final ApiError error;

    private ApiResponse(T data, ApiError error) {
        this.data = data;
        this.error = error;
        timestamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this(data, null);
    }

    public ApiResponse(ApiError error) {
        this(null, error);
    }
}
