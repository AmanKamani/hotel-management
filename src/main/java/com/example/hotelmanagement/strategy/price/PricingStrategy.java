package com.example.hotelmanagement.strategy.price;

import com.example.hotelmanagement.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculatePrice(Inventory inventory);
}
