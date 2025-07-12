package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestResponse {
    private Long id;
    private String name;
    private Gender gender;
    private Integer age;
}
