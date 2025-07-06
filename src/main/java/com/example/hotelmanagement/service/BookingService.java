package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.request.BookingRequest;
import com.example.hotelmanagement.dto.response.BookingResponse;

public interface BookingService {
    BookingResponse initializeBooking(BookingRequest request);
}
