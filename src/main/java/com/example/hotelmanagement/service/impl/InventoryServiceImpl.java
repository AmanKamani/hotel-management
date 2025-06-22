package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.Inventory;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.repository.InventoryRepository;
import com.example.hotelmanagement.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public void createRoomsInventoryForAYear(List<Room> rooms) {
        rooms.forEach(room -> {
            LocalDateTime current = LocalDateTime.now();
            final LocalDateTime upto = current.plusYears(1);

            final List<Inventory> inventories = new ArrayList<>();
            for(; current.isBefore(upto); current = current.plusDays(1)) {
                inventories.add(createInventory(room, current));
            }

            int totalInventoriesCreated = inventoryRepository.saveAll(inventories).size();
            log.info("Added total [{}] inventory for hotel [{}], room [{}]", totalInventoriesCreated, room.getHotel().getName(), room.getType());
        });
    }

    private Inventory createInventory(Room room, LocalDateTime dateTime) {
        return Inventory.builder()
                .hotel(room.getHotel())
                .room(room)
                .dateTime(dateTime)
                .bookedCount(0)
                .totalCount(room.getTotalCount())
                .city(room.getHotel().getCity())
                .closed(false)
                .price(room.getBasePrice())
                .surgeFactor(BigDecimal.ONE)
                .build();
    }
}
