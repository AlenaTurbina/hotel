package com.hotel.service;

import com.hotel.dto.RoomTypeDTO;
import com.hotel.model.entity.RoomType;

import java.util.List;

public interface RoomTypeService {
    List<RoomType> getAll();

    RoomType getById (Integer id);

    void save(RoomType roomType);

    RoomType saveByDTO (RoomTypeDTO roomTypeDTO);
}
