package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.dto.response.HotelMinPriceResponse;
import com.example.hotelmanagement.entity.Hotel;
import com.example.hotelmanagement.entity.HotelMinPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HotelMinPriceRepository extends JpaRepository<HotelMinPrice, Long> {

    @Query("""
            SELECT new com.example.hotelmanagement.dto.response.HotelMinPriceResponse(h.hotel, AVG(h.price))
            FROM HotelMinPrice h
            WHERE h.hotel.city = :city
                AND h.date BETWEEN :startDate AND :endDate
                AND h.hotel.active = true
            GROUP BY h.hotel
            """)
    Page<HotelMinPriceResponse> findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );

    Optional<HotelMinPrice> findByHotelAndDate(Hotel hotel, LocalDate date);

}
