package com.hotel.service;

import com.hotel.dto.OrderBookingDTO;
import com.hotel.model.entity.OrderBooking;
import com.hotel.model.entity.Room;
import com.hotel.model.entity.RoomKind;
import com.hotel.model.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OrderBookingService {
    List<OrderBooking> getAll();

    OrderBooking getById(Integer id);

    Double calculationSumTotal(OrderBookingDTO orderBookingDTO, Room room);

    Room getFirstFreeRoom(OrderBookingDTO orderBookingDTO);

    OrderBooking save(OrderBookingDTO orderBookingDTO);

    OrderBooking save1(OrderBookingDTO orderBookingDTO);

    void save(OrderBooking orderBooking);

    Map<RoomKind, Long> getFreeRooms (OrderBookingDTO orderBookingDTO);

    OrderBooking update(OrderBookingDTO orderBookingDTO, OrderBooking orderBooking);

    OrderBooking update(OrderBooking orderBooking);

}
