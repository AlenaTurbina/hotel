package com.hotel.service.impl;

import com.hotel.model.dao.OrderBookingRepository;
import com.hotel.model.entity.OrderBooking;
import com.hotel.model.entity.Room;
import com.hotel.service.OrderBookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderBookingServiceImpl implements OrderBookingService {
    OrderBookingRepository orderBookingRepository;

    @Override
    public List<OrderBooking> getAll() {
        return orderBookingRepository.findAll();
    }

    @Override
    public void save(OrderBooking orderBooking) {
        orderBookingRepository.saveAndFlush(orderBooking);
    }

}
