package com.hotel.service.impl;

import com.hotel.dto.RoomTypeDTO;
import com.hotel.model.repository.RoomTypeRepository;
import com.hotel.model.entity.RoomType;
import com.hotel.service.RoomTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {
    private RoomTypeRepository roomTypeRepository;

    @Override
    public List<RoomType> getAll() {
        return roomTypeRepository.findAll();
    }

    @Override
    public RoomType getById(Integer id) {
        return roomTypeRepository.getById(id);
    }

    @Override
    public void save(RoomType roomType) {
        roomTypeRepository.saveAndFlush(roomType);
    }

    @Override
    public RoomType saveByDTO(RoomTypeDTO roomTypeDTO) {
        var roomType = RoomType.builder()
                .name(roomTypeDTO.getName())
                .quantityPlaces(roomTypeDTO.getQuantityPlaces())
                .build();

        return roomTypeRepository.saveAndFlush(roomType);
    }
}
