package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.dto.request.RoomRequest;
import com.example.hotelmanagement.dto.response.RoomResponse;
import com.example.hotelmanagement.entity.Hotel;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.repository.HotelRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import com.example.hotelmanagement.service.InventoryService;
import com.example.hotelmanagement.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    @Transactional
    public RoomResponse create(Long hotelId, RoomRequest roomRequest) {
        Hotel hotel = getHotelByIdOrThrow(hotelId);

        Room room = modelMapper.map(roomRequest, Room.class);
        room.setHotel(hotel);

        Room roomSaved = roomRepository.save(room);
        log.info("Room created with ID [{}] for Hotel [{}] Successfully.", roomSaved.getId(), hotelId);

        if (Boolean.TRUE.equals(hotel.getActive())) {
            inventoryService.createRoomsInventoryForAYear(List.of(roomSaved));
            log.info("Added inventory for a year for room [{}]", roomSaved.getId());
        }

        return modelMapper.map(roomSaved, RoomResponse.class);
    }

    @Override
    public RoomResponse getById(Long hotelId, Long roomId) {
        Room room = getRoomByIdOrThrow(hotelId, roomId);
        return modelMapper.map(room, RoomResponse.class);
    }

    @Override
    public List<RoomResponse> getAll(Long hotelId) {
        Hotel hotel = getHotelByIdOrThrow(hotelId);

        return hotel.getRooms()
                .stream()
                .map(r -> modelMapper.map(r, RoomResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponse update(Long hotelId, Long roomId, RoomRequest roomRequest) {
        Room room = getRoomByIdOrThrow(hotelId, roomId);

        modelMapper.map(roomRequest, room);

        Room savedRoom = roomRepository.save(room);
        log.info("Room updated with id [{}]", savedRoom.getId());
        return modelMapper.map(savedRoom, RoomResponse.class);
    }

    @Override
    // TODO: Add transactional annotation
    public Void delete(Long hotelId, Long roomId) {
        if (!roomRepository.existsRoomByIdAndHotel_Id(roomId, hotelId)) {
            throw new ResourceNotFoundException(String.format(String.format("Room [%s] Not Found for hotel [%s]",  roomId, hotelId)));
        }

        // TODO: call to delete inventory
        roomRepository.deleteById(roomId);
        return null;
    }

    private void checkHotelExistsOrThrow(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Hotel with id %s not found", id));
        }
    }

    private Hotel getHotelByIdOrThrow(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + id));
    }

    private Room getRoomByIdOrThrow(Long hotelId, Long roomId) {
        return roomRepository.findRoomByIdAndHotel_Id(roomId, hotelId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Room [%s] Not Found for hotel [%s]",  roomId, hotelId)));
    }
}
