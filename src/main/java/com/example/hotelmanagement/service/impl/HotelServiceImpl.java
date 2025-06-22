package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.dto.request.HotelRequest;
import com.example.hotelmanagement.dto.response.HotelResponse;
import com.example.hotelmanagement.entity.Hotel;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.repository.HotelRepository;
import com.example.hotelmanagement.service.HotelService;
import com.example.hotelmanagement.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper mapper;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;


    @Override
    public HotelResponse create(HotelRequest hotelRequest) {
        Hotel hotel = mapper.map(hotelRequest, Hotel.class);
        Hotel savedHotel = hotelRepository.save(hotel);
        return mapper.map(savedHotel, HotelResponse.class);
    }

    @Override
    public HotelResponse getById(Long id) {
        Hotel hotel = getHotelByIdOrThrow(id);
        return mapper.map(hotel, HotelResponse.class);
    }

    @Override
    public HotelResponse update(Long id, HotelRequest hotelRequest) {
        Hotel hotel = getHotelByIdOrThrow(id);
        modelMapper.map(hotelRequest, hotel);

        Hotel savedHotel = hotelRepository.save(hotel);

        return mapper.map(savedHotel, HotelResponse.class);
    }

    @Override
    public Void delete(Long id) {
        Hotel hotel = getHotelByIdOrThrow(id);
        hotelRepository.delete(hotel);
        // automatically deletes room, because of orphan removal
        // room entity also has orphan removal for inventory
        // So, deleting hotel will delete all the rooms & related inventories

        return null;
    }

    @Override
    @Transactional
    public Void activate(Long id) {
        Hotel hotel = getHotelByIdOrThrow(id);
        if (hotel.getActive()) {
            log.info("Hotel [{}] is already active.", hotel.getId());
            return null;
        }
        hotel.setActive(true);

        hotelRepository.save(hotel);

        inventoryService.createRoomsInventoryForAYear(hotel.getRooms());

        log.info("Activated hotel [{}] and created inventory for all rooms", hotel.getId());
        return null;
    }

    private Hotel getHotelByIdOrThrow(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + id));
    }
}
