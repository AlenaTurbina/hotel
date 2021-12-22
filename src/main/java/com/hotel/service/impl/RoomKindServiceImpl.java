package com.hotel.service.impl;

import com.hotel.model.entity.RoomKind;
import com.hotel.model.repository.RoomKindRepository;
import com.hotel.service.RoomKindService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomKindServiceImpl implements RoomKindService {
    private RoomKindRepository roomKindRepository;

    @Override
    public List<RoomKind> getAll() {
        return roomKindRepository.findAll();
    }




}
