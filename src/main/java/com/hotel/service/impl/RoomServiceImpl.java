package com.hotel.service.impl;

import com.hotel.dto.RoomDTO;
import com.hotel.model.entity.ClassApartment;
import com.hotel.model.entity.RoomKind;
import com.hotel.model.entity.RoomType;
import com.hotel.model.repository.RoomRepository;
import com.hotel.model.entity.Room;
import com.hotel.service.RoomKindService;
import com.hotel.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RoomServiceImpl implements RoomService{
    private RoomRepository roomRepository;
    private RoomKindService roomKindService;

    @Override
    public List<Room> getAll() {
        log.info("Room getAll");
        return roomRepository.findAll();
    }

    @Override
    public Room getById(Integer id) {
        var room = roomRepository.getById(id);
        if (room != null) {
            log.info("Room getById is not null (id): " + id);
            return room;
        } else {
            log.info("Room getById is null(id): " + id);
            return null;
        }
    }

    @Override
    public Room getByName(String name) {
        return roomRepository.findRoomByName(name);
    }

    @Override
    public List<ClassApartment> getListUniqueClassApartmentsFromRooms() {
        log.info("Get list unique class apartments from rooms");
        return roomRepository.findAll().stream()
                .map(room -> room.getRoomKind().getClassApartment())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomType> getListUniqueRoomTypesFromRooms() {
        log.info("Get list unique room types from rooms");
        return roomRepository.findAll().stream()
                .map(room -> room.getRoomKind().getRoomType())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomKind> getListUniqueRoomKindsFromRooms() {
        log.info("Get list unique room kinds from rooms");
        return roomRepository.findAll().stream()
                .map(room -> room.getRoomKind())
                .distinct()
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public Room save(RoomDTO roomDTO) {
        var room = Room.builder()
                .name(roomDTO.getName())
                .roomKind(roomKindService.getById(roomDTO.getRoomKind()))
                .build();
        log.info("New Room build (name, roomKindID): " + roomDTO.getName() + ", " + roomDTO.getRoomKind());
        return roomRepository.saveAndFlush(room);
    }

    @Override
    @Transactional
    public void update(Integer id, String name, RoomKind roomKind) {
        var roomUpdate = roomRepository.findById(id);
        var roomNew = roomUpdate.get();
        roomNew.setName(name);
        roomNew.setRoomKind(roomKind);
        roomRepository.saveAndFlush(roomNew);
        log.info("Room update (id): " + id);
    }

}
