package com.example.hotelmanagement.strategy.price;

import com.example.hotelmanagement.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class SurgePricingStrategy implements PricingStrategy {
    private final PricingStrategy strategy;


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return strategy.calculatePrice(inventory).multiply(inventory.getSurgeFactor());
    }
}
