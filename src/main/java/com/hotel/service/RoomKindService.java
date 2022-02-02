package com.hotel.service;

import com.hotel.dto.RoomKindDTO;
import com.hotel.model.entity.ClassApartment;
import com.hotel.model.entity.RoomKind;
import com.hotel.model.entity.RoomType;

import java.util.List;

public interface RoomKindService {
    List<RoomKind> getAll();

    RoomKind getById(Integer id);

    List<ClassApartment> getListUniqueClassApartmentsFromRoomKinds();

    List<RoomType> getListUniqueRoomTypesFromRoomKinds();

    Integer getRoomKindByRoomTypeAndClassApartmentID(Integer roomTypeID, Integer classApartmentID);

    RoomKind save(RoomKindDTO roomKindDTO);

    void update(Integer id, RoomType roomType, ClassApartment classApartment, Double roomPrice);

}
