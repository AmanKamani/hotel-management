package com.example.hotelmanagement.dto.request;

import com.example.hotelmanagement.entity.ContactInfo;
import lombok.Data;


@Data
public class HotelRequest {
    private String name;

    private String city;

    private String[] photos;

    private String[] amenities;

    private ContactInfo contactInfo;

    private Boolean active;

}
