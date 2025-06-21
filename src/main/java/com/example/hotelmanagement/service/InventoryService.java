package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface InventoryService {

    void createRoomsInventoryForAYear(List<Room> rooms);

    void deleteFutureInventoryForRooms(LocalDateTime dateTime, List<Room> rooms);
}
