package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    Optional<Room> findRoomByIdAndHotel_Id(Long id, Long hotelId);

    Boolean existsRoomByIdAndHotel_Id(Long id, Long hotelId);
}
