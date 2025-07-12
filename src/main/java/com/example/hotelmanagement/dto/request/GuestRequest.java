package com.example.hotelmanagement.dto.request;

import com.example.hotelmanagement.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestRequest {
    private Long id;
    private String name;
    private Integer age;
    private Gender gender;
}
