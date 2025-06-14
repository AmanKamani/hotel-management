package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.request.RoomRequest;
import com.example.hotelmanagement.dto.response.RoomResponse;

import java.util.List;

public interface RoomService {

    RoomResponse create(Long hotelId, RoomRequest roomRequest);

    RoomResponse getById(Long hotelId, Long roomId);

    List<RoomResponse> getAll(Long hotelId);

    RoomResponse update(Long hotelId, Long roomId, RoomRequest roomRequest);

    Void delete(Long hotelId, Long roomId);
}
