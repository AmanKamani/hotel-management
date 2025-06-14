package com.example.hotelmanagement.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomRequest {

    private String type;

    private String[] photos;

    private String[] amenities;

    private BigDecimal basePrice;

    private Integer totalCount;

    private Integer capacity;
}
