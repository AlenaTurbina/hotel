package com.hotel.service;

import com.hotel.model.entity.OrderStatus;

import java.util.List;

public interface OrderStatusService {
    List<OrderStatus> getAll();

    OrderStatus getById(Integer id);
}
