package com.hotel.service;

import com.hotel.model.entity.OrderBooking;

import java.util.List;

public interface OrderBookingService {
    List<OrderBooking> getAll();

    void save(OrderBooking orderBooking);
}
