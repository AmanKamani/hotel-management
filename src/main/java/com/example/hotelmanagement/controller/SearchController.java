package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.request.HotelSearchRequest;
import com.example.hotelmanagement.dto.response.HotelInfoResponse;
import com.example.hotelmanagement.dto.response.HotelMinPriceResponse;
import com.example.hotelmanagement.service.HotelService;
import com.example.hotelmanagement.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search/hotel")
@RequiredArgsConstructor
public class SearchController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @PostMapping
    public Page<HotelMinPriceResponse> searchHotels(@RequestBody HotelSearchRequest request) {
        return inventoryService.searchHotels(request);
    }

    @GetMapping("/{hotelId}/info")
    public HotelInfoResponse getHotelInfo(@PathVariable("hotelId") Long hotelId) {
        return hotelService.getHotelInfo(hotelId);
    }
}
