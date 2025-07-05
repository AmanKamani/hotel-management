package com.example.hotelmanagement.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class HotelInfoResponse {

    private HotelResponse hotel;
    private List<RoomResponse> rooms;
}
