package com.example.hotelmanagement.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RoomResponse {

    private Long id;

    private String type;

    private String[] photos;

    private String[] amenities;

    private BigDecimal basePrice;

    private Integer totalCount;

    private Integer capacity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
