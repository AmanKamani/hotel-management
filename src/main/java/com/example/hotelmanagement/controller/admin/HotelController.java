package com.example.hotelmanagement.controller.admin;

import com.example.hotelmanagement.dto.request.HotelRequest;
import com.example.hotelmanagement.dto.response.HotelResponse;
import com.example.hotelmanagement.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponse getById(@PathVariable Long id) {
        return hotelService.getById(id);
    }

    @PostMapping
    public HotelResponse create(@RequestBody HotelRequest hotelRequest) {
        return hotelService.create(hotelRequest);
    }

    @PutMapping("/{id}")
    public HotelResponse update(@PathVariable Long id, @RequestBody HotelRequest hotelRequest) {
        return hotelService.update(id, hotelRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Void delete(@PathVariable Long id) {
        return hotelService.delete(id);
    }

    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Void activate(@PathVariable Long id) {
        return hotelService.activate(id);
    }
}
