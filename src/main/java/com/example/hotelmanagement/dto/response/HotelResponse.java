package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.entity.ContactInfo;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class HotelResponse {

    private Long id;

    private String name;

    private String city;

    private String[] photos;

    private String[] amenities;

    private ContactInfo contactInfo;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

}
