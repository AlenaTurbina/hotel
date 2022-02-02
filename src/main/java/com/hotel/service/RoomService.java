package com.hotel.service;


import com.hotel.dto.RoomDTO;
import com.hotel.model.entity.ClassApartment;
import com.hotel.model.entity.Room;
import com.hotel.model.entity.RoomKind;
import com.hotel.model.entity.RoomType;

import java.util.List;

public interface RoomService {
    List<Room> getAll();

    Room getById(Integer id);

    Room getByName(String name);

    List<ClassApartment> getListUniqueClassApartmentsFromRooms();

    List<RoomType> getListUniqueRoomTypesFromRooms();

    List<RoomKind> getListUniqueRoomKindsFromRooms();

    Room save(RoomDTO roomDTO);

    void update(Integer id, String name, RoomKind roomKind);

}
