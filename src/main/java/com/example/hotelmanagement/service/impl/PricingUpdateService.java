package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.Hotel;
import com.example.hotelmanagement.entity.HotelMinPrice;
import com.example.hotelmanagement.entity.Inventory;
import com.example.hotelmanagement.repository.HotelMinPriceRepository;
import com.example.hotelmanagement.repository.HotelRepository;
import com.example.hotelmanagement.repository.InventoryRepository;
import com.example.hotelmanagement.strategy.price.PricingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PricingUpdateService {

    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final PricingService pricingService;

    @Scheduled(cron = "0 0 * * * *") // every hour
//    @Scheduled(cron = "*/5 * * * * *") // every 5 seconds
    public void updatePrices() {
        int page = 0;
        int batchSize = 100;

        while (true) {
            Page<Hotel> hotels = hotelRepository.findAll(PageRequest.of(page, batchSize));

            if (hotels.isEmpty()) break;

            page++;

            hotels.getContent().forEach(this::updateHotelPrices);
        }

    }

    private void updateHotelPrices(Hotel hotel) {
        log.info("Update hotel prices for hotel [{}]", hotel.getId());
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);

        List<Inventory> inventories = inventoryRepository.findByHotelAndDateBetween(hotel, startDate, endDate);

        updateInventoryPrices(inventories);
        updateHotelMinPrice(hotel, inventories, startDate, endDate);

    }

    private void updateInventoryPrices(List<Inventory> inventories) {
        inventories.forEach(inventory -> {
            BigDecimal price = pricingService.calculatePrice(inventory);
            inventory.setPrice(price);
        });

        inventoryRepository.saveAll(inventories);
    }

    private void updateHotelMinPrice(Hotel hotel, List<Inventory> inventories, LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, BigDecimal> dailyMinPrices = inventories.stream()
                .collect(Collectors.groupingBy(
                        Inventory::getDate,
                        Collectors.mapping(Inventory::getPrice, Collectors.minBy(Comparator.naturalOrder()))
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().orElse(BigDecimal.ZERO)));

        List<HotelMinPrice> hotelMinPrices = new ArrayList<>();
        dailyMinPrices.forEach((date, price) -> {
            HotelMinPrice hotelMinPrice = hotelMinPriceRepository.findByHotelAndDate(hotel, date)
                    .orElse(new HotelMinPrice(hotel, date));
            hotelMinPrice.setPrice(price);
            hotelMinPrices.add(hotelMinPrice);
        });

        hotelMinPriceRepository.saveAll(hotelMinPrices);
    }
}
