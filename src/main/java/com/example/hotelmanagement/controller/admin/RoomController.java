package com.example.hotelmanagement.controller.admin;

import com.example.hotelmanagement.dto.request.RoomRequest;
import com.example.hotelmanagement.dto.response.RoomResponse;
import com.example.hotelmanagement.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotel/{hotelId}/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse create(@PathVariable Long hotelId, @RequestBody RoomRequest roomRequest) {
        return roomService.create(hotelId, roomRequest);
    }

    @GetMapping
    public List<RoomResponse> getByHotelId(@PathVariable Long hotelId) {
        return roomService.getAll(hotelId);
    }

    @GetMapping("/{roomId}")
    public RoomResponse getById(@PathVariable Long hotelId, @PathVariable Long roomId) {
        return roomService.getById(hotelId, roomId);
    }

    @PutMapping("/{roomId}")
    public RoomResponse update(@PathVariable Long hotelId, @PathVariable Long roomId, @RequestBody RoomRequest roomRequest) {
        return roomService.update(hotelId, roomId, roomRequest);
    }

    @DeleteMapping("/{roomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long hotelId, @PathVariable Long roomId) {
        roomService.delete(hotelId, roomId);
    }
}
