package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.request.HotelSearchRequest;
import com.example.hotelmanagement.dto.response.HotelResponse;
import com.example.hotelmanagement.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class SearchController {

    private final InventoryService inventoryService;

    @PostMapping("/search")
    public Page<HotelResponse> searchHotels(@RequestBody HotelSearchRequest request) {
        return inventoryService.searchHotels(request);
    }
}
