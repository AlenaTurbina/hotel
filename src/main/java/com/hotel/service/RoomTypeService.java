package com.hotel.service;

import com.hotel.dto.RoomTypeDTO;
import com.hotel.model.entity.RoomType;

import java.util.List;

public interface RoomTypeService {
    List<RoomType> getAll();

    RoomType getById(Integer id);

    RoomType getByName(String name);

    void update(RoomType roomType);

    RoomType save(RoomTypeDTO roomTypeDTO);
}
