package com.hotel.service;

import com.hotel.dto.OrderBookingDTO;
import com.hotel.model.entity.OrderBooking;
import com.hotel.model.entity.Room;
import com.hotel.model.entity.RoomKind;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderBookingService {
    List<OrderBooking> getAll();

    OrderBooking getById(Integer id);

    Double calculationSumTotal(OrderBookingDTO orderBookingDTO, Room room);

    Room getFirstRelevantFreeRoom(OrderBookingDTO orderBookingDTO);

    OrderBooking save(OrderBookingDTO orderBookingDTO);

    Map<RoomKind, Long> getFreeRooms (OrderBookingDTO orderBookingDTO);

    List<Room> getListFreeRoomsOnOrderBookingDates(OrderBooking orderBooking);

    List<Room> getBusyRoomsOnSelectedDates(LocalDate dateArrival, LocalDate dateDeparture);

    OrderBooking update(OrderBooking orderBooking);


}
