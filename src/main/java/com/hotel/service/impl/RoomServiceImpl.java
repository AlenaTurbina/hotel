package com.hotel.service.impl;

import com.hotel.model.repository.RoomRepository;
import com.hotel.model.entity.Room;
import com.hotel.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService{
    private RoomRepository roomRepository;

    @Override
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room getById(Integer id) {
        return roomRepository.getById(id);
    }


}
