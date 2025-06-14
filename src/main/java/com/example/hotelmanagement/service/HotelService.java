package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.request.HotelRequest;
import com.example.hotelmanagement.dto.response.HotelResponse;

public interface HotelService {

    HotelResponse create(HotelRequest hotelRequest);

    HotelResponse getById(Long id);

    HotelResponse update(Long id, HotelRequest hotelRequest);

    Void delete(Long id);
}
