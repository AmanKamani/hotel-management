package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.request.BookingRequest;
import com.example.hotelmanagement.dto.response.BookingResponse;
import com.example.hotelmanagement.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/init")
    public BookingResponse initializeBooking(@RequestBody BookingRequest request) {
        return bookingService.initializeBooking(request);
    }
}
