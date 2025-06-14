package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.dto.request.HotelRequest;
import com.example.hotelmanagement.dto.response.HotelResponse;
import com.example.hotelmanagement.entity.Hotel;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.repository.HotelRepository;
import com.example.hotelmanagement.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper mapper;
    private final ModelMapper modelMapper;


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
        return null;
    }

    private Hotel getHotelByIdOrThrow(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + id));
    }
}
