package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.dto.request.BookingRequest;
import com.example.hotelmanagement.dto.request.GuestRequest;
import com.example.hotelmanagement.dto.response.BookingResponse;
import com.example.hotelmanagement.entity.*;
import com.example.hotelmanagement.entity.enums.BookingStatus;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.GuestRepository;
import com.example.hotelmanagement.repository.InventoryRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import com.example.hotelmanagement.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final GuestRepository guestRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public BookingResponse initializeBooking(BookingRequest request) {
        String requestStr = request.toString();
        log.info("Initializing Booking for request [{}]", requestStr);
        Room room = getRoomByIdOrThrow(request.getHotelId(), request.getRoomId());

        List<Inventory> inventory = inventoryRepository.findAndLockAvailableInventory(
                request.getRoomId(),
                request.getCheckInDate(),
                request.getCheckOutDate(),
                request.getRoomCount()
        );

        long days = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate()) + 1;

        if (days != inventory.size()) {
            log.info("Enough rooms are not available for booking request [{}]", requestStr);
            throw new IllegalStateException("Rooms are not available");
        }

        // update inventory
        for (Inventory inventoryItem : inventory) {
            inventoryItem.setReservedCount(inventoryItem.getReservedCount() + request.getRoomCount());
        }

        inventoryRepository.saveAll(inventory);

        // save booking
        User user = getUser();

        Booking booking = Booking.builder()
                .hotel(room.getHotel())
                .room(room)
                .status(BookingStatus.RESERVED)
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .roomsCount(request.getRoomCount())
                .amount(BigDecimal.TEN)        // TODO: Add dynamic price count
                .user(user)
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        log.info("Booking confirmed with BookingID [{}] for request [{}]", savedBooking.getId(), requestStr);
        return mapper.map(savedBooking, BookingResponse.class);
    }

    @Override
    @Transactional
    public BookingResponse addGuest(Long bookingId, List<GuestRequest> guests) {
        Booking booking = getBookingOrThrow(bookingId);

        if (isExpired(booking)) {
            log.info("Cannot add guests, Booking is expired for BookingID [{}]", bookingId);
            throw new IllegalStateException("Booking is expired");
        }

        if (booking.getStatus() != BookingStatus.RESERVED) {
            log.info("Cannot add guests, Booking [{}} current status is [{}]", bookingId, booking.getStatus());
            throw new IllegalStateException("Booking is in invalid state");
        }

        // save all guests
        User user = getUser();
        final Set<Guest> guestsEntity = new HashSet<>();
        for (GuestRequest guestRequest : guests) {
            Guest guest = mapper.map(guestRequest, Guest.class);
            guest.setUser(user);
            guestsEntity.add(guest);
        }

        List<Guest> savedGuests = guestRepository.saveAll(guestsEntity);
        log.info("Added [{}] guests for BookingID [{}]", guestsEntity.size(), bookingId);

        // update booking
        booking.setStatus(BookingStatus.GUEST_ADDED);
        booking.getGuests().addAll(new HashSet<>(savedGuests));
        Booking savedBooking = bookingRepository.save(booking);
        log.info("Updated booking [{}] with status [{}] and [{}] guests", bookingId, booking.getStatus(), savedGuests.size());

        return mapper.map(savedBooking, BookingResponse.class);
    }

    private User getUser() {
        // TODO: Remove static user
        User user = new User();
        user.setId(1L);
        return user;
    }

    private Booking getBookingOrThrow(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Booking not found with id [%s]", bookingId)));
    }

    public boolean isExpired(Booking booking) {
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    private Room getRoomByIdOrThrow(Long hotelId, Long roomId) {
        return roomRepository.findRoomByIdAndHotel_Id(roomId, hotelId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Room [%s] Not Found for hotel [%s]", roomId, hotelId)));
    }
}
