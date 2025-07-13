package com.example.hotelmanagement.strategy.price;

import com.example.hotelmanagement.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingService {

    public BigDecimal calculatePrice(Inventory inventory) {
        PricingStrategy strategy = new BasePricingStrategy();
        strategy = new SurgePricingStrategy(strategy);
        strategy = new OccupancyPricingStrategy(strategy);
        strategy = new UrgencyPricingStrategy(strategy);
        strategy = new HolidayPricingStrategy(strategy);

        return strategy.calculatePrice(inventory);
    }
}
