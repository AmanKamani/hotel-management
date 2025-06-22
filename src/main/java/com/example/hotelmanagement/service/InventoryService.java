package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Room;

import java.util.List;

public interface InventoryService {

    void createRoomsInventoryForAYear(List<Room> rooms);
}
