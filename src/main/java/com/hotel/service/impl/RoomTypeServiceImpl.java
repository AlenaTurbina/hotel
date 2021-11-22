package com.hotel.service.impl;

import com.hotel.model.dao.RoomTypeRepository;
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
}
