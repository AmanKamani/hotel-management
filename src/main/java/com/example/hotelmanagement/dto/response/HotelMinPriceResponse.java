package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelMinPriceResponse {
    private Hotel hotel;
    private Double price;
}
