package com.example.hotelmanagement.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class HotelSearchRequest {
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer requiredRoom;
    private LocalDateTime temp;

    private Integer page = 0;
    private Integer pageSize = 10;
}
