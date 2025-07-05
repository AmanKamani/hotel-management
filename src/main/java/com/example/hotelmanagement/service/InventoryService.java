package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.request.HotelSearchRequest;
import com.example.hotelmanagement.dto.response.HotelResponse;
import com.example.hotelmanagement.entity.Room;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {

    void createRoomsInventoryForAYear(List<Room> rooms);

    Page<HotelResponse> searchHotels(HotelSearchRequest searchRequest);
}
