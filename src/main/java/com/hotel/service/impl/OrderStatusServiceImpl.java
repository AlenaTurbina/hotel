package com.hotel.service.impl;

import com.hotel.model.repository.OrderStatusRepository;
import com.hotel.model.entity.OrderStatus;
import com.hotel.service.OrderStatusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private OrderStatusRepository orderStatusRepository;

    @Override
    public List<OrderStatus> getAll() {
        log.info("OrderStatus getAll");
        return orderStatusRepository.findAll();
    }

    @Override
    public OrderStatus getById(Integer id) {
        var orderStatus = orderStatusRepository.getById(id);
        if (orderStatus != null) {
            log.info("OrderStatus getById is not null (id): " + id);
            return orderStatus;
        } else {
            log.info("OrderStatus getById is null(id): " + id);
            return null;
        }
    }

}
