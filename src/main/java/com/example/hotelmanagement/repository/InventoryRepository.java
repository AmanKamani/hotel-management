package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.Inventory;
import com.example.hotelmanagement.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    void deleteByDateTimeAfterAndRoom(LocalDateTime dateTime, Room room);
}
