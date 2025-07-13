package com.example.hotelmanagement.strategy.price;

import com.example.hotelmanagement.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStrategy {
    private final PricingStrategy strategy;


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = strategy.calculatePrice(inventory);
        LocalDate today = LocalDate.now();
        long days = ChronoUnit.DAYS.between(today, inventory.getDate());
        if (days <= 7) {
            price = price.multiply(BigDecimal.valueOf(1.15));
        }

        return price;
    }
}
