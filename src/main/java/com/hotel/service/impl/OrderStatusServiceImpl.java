package com.hotel.service.impl;

import com.hotel.model.repository.OrderStatusRepository;
import com.hotel.model.entity.OrderStatus;
import com.hotel.service.OrderStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private OrderStatusRepository orderStatusRepository;

    @Override
    public List<OrderStatus> getAll() {
        return orderStatusRepository.findAll();
    }

    @Override
    public OrderStatus getById(Integer id) {
        return orderStatusRepository.getById(id);
    }

}
