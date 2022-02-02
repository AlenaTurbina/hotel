package com.hotel.service.impl;

import com.hotel.dto.RoomTypeDTO;
import com.hotel.model.repository.RoomTypeRepository;
import com.hotel.model.entity.RoomType;
import com.hotel.service.RoomTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {
    private RoomTypeRepository roomTypeRepository;

    @Override
    public List<RoomType> getAll() {
        log.info("RoomType getAll");
        return roomTypeRepository.findAll();
    }

    @Override
    public RoomType getById(Integer id) {
        var roomType = roomTypeRepository.getById(id);
        if (roomType != null) {
            log.info("RoomType getById is not null (id): " + id);
            return roomType;
        } else {
            log.info("RoomType getById is null(id): " + id);
            return null;
        }
    }

    @Override
    public RoomType getByName(String name) {
        log.info("Room type getByName (name): " + name);
        return roomTypeRepository.findRoomTypeByName(name);
    }

    @Override
    @Transactional
    public RoomType save(RoomTypeDTO roomTypeDTO) {
        var roomType = RoomType.builder()
                .name(roomTypeDTO.getName())
                .quantityPlaces(roomTypeDTO.getQuantityPlaces())
                .build();
        log.info("New RoomType build (name, places): " + roomTypeDTO.getName() + ", " + roomTypeDTO.getQuantityPlaces());
        return roomTypeRepository.saveAndFlush(roomType);
    }

    @Override
    @Transactional
    public void update(RoomType roomType) {
        roomTypeRepository.saveAndFlush(roomType);
        log.info("RoomType update (id): " + roomType.getId());
    }

}
