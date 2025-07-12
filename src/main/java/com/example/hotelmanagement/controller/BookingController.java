package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.request.BookingRequest;
import com.example.hotelmanagement.dto.request.GuestRequest;
import com.example.hotelmanagement.dto.response.BookingResponse;
import com.example.hotelmanagement.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/init")
    public BookingResponse initializeBooking(@RequestBody BookingRequest request) {
        return bookingService.initializeBooking(request);
    }

    @PostMapping("/{bookingId}/addGuest")
    public BookingResponse addGuest(@PathVariable Long bookingId, @RequestBody List<GuestRequest> request) {
        return bookingService.addGuest(bookingId, request);
    }
}
