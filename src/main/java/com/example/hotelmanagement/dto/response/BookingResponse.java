package com.example.hotelmanagement.dto.response;


import com.example.hotelmanagement.entity.User;
import com.example.hotelmanagement.entity.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class BookingResponse {

    private Long id;
    private User user;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BookingStatus status;
    private Set<GuestResponse> guests;
}
