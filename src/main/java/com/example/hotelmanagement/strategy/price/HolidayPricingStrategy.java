package com.example.hotelmanagement.strategy.price;

import com.example.hotelmanagement.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy {

    private final PricingStrategy strategy;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = strategy.calculatePrice(inventory);
        boolean isHoliday = true; // call api to check whether it is holiday or not

        if (isHoliday) {
            price = price.multiply(BigDecimal.valueOf(1.25));
        }

        return price;
    }

}
