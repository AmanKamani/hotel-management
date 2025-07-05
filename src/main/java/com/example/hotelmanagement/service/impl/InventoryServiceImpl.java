package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.dto.request.HotelSearchRequest;
import com.example.hotelmanagement.dto.response.HotelResponse;
import com.example.hotelmanagement.entity.Hotel;
import com.example.hotelmanagement.entity.Inventory;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.repository.InventoryRepository;
import com.example.hotelmanagement.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createRoomsInventoryForAYear(List<Room> rooms) {
        rooms.forEach(room -> {
            LocalDate current = LocalDate.now();
            final LocalDate upto = current.plusYears(1);

            final List<Inventory> inventories = new ArrayList<>();
            for(; current.isBefore(upto); current = current.plusDays(1)) {
                inventories.add(createInventory(room, current));
            }

            int totalInventoriesCreated = inventoryRepository.saveAll(inventories).size();
            log.info("Added total [{}] inventory for hotel [{}], room [{}]", totalInventoriesCreated, room.getHotel().getName(), room.getType());
        });
    }

    @Override
    public Page<HotelResponse> searchHotels(HotelSearchRequest searchRequest) {
        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getPageSize());
        final long dateCount = ChronoUnit.DAYS.between(searchRequest.getStartDate(), searchRequest.getEndDate()) + 1;

        Page<Hotel> hotels = inventoryRepository.findHotelsWithAvailableInventory(
                searchRequest.getCity(),
                searchRequest.getStartDate(),
                searchRequest.getEndDate(),
                searchRequest.getRequiredRoom(),
                dateCount,
                pageable
        );

        return hotels.map(hotel -> modelMapper.map(hotel, HotelResponse.class));
    }

    private Inventory createInventory(Room room, LocalDate date) {
        return Inventory.builder()
                .hotel(room.getHotel())
                .room(room)
                .date(date)
                .bookedCount(0)
                .totalCount(room.getTotalCount())
                .city(room.getHotel().getCity())
                .closed(false)
                .price(room.getBasePrice())
                .surgeFactor(BigDecimal.ONE)
                .build();
    }
}
