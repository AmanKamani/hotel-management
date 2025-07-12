package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.request.BookingRequest;
import com.example.hotelmanagement.dto.request.GuestRequest;
import com.example.hotelmanagement.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse initializeBooking(BookingRequest request);

    BookingResponse addGuest(Long bookingId, List<GuestRequest> guests);
}
