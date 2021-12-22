package com.hotel.service;

import com.hotel.dto.OrderBookingDTO;
import com.hotel.model.entity.Room;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface RoomService {
    List<Room> getAll();

    Room getById(Integer id);

    Room checkRoom(Integer roomTypeId, Integer classApartmentId);

    Room checkRoomNew(OrderBookingDTO orderBookingDTO);
}
