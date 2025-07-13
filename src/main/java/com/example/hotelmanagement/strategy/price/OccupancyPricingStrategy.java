package com.example.hotelmanagement.strategy.price;

import com.example.hotelmanagement.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class OccupancyPricingStrategy implements PricingStrategy {
    private final PricingStrategy strategy;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = strategy.calculatePrice(inventory);
        double occupanceRate = ((double) inventory.getBookedCount()) / inventory.getTotalCount();

        if (occupanceRate > 0.8) {
            price = price.multiply(BigDecimal.valueOf(1.2));
        }

        return price;
    }
}
