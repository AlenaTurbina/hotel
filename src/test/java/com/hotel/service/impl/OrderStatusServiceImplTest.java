package com.hotel.service.impl;

import com.hotel.model.entity.OrderStatus;
import com.hotel.model.repository.OrderStatusRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class OrderStatusServiceImplTest {
    @Mock
    private OrderStatusRepository orderStatusRepository;

    @InjectMocks
    private OrderStatusServiceImpl orderStatusService;

    private OrderStatus orderStatus;

    @Test
    void testGetAll() {
        Integer id1 = 1;
        Integer id2 = 2;
        Integer id3 = 3;
        Integer id4 = 4;
        OrderStatus orderStatus1 = new OrderStatus(id1, null);
        OrderStatus orderStatus2 = new OrderStatus(id2, null);
        OrderStatus orderStatus3 = new OrderStatus(id3, null);
        OrderStatus orderStatus4 = new OrderStatus(id4, null);

        List<OrderStatus> orderStatuses = new ArrayList<>(List.of(orderStatus1, orderStatus2, orderStatus3, orderStatus4));
        Mockito.when(orderStatusRepository.findAll()).thenReturn(orderStatuses);
        List<OrderStatus> orderStatusesExpected = new ArrayList<>(List.of(orderStatus1, orderStatus2, orderStatus3, orderStatus4));
        List<OrderStatus> orderStatusesActual = orderStatusService.getAll();

        Assert.assertEquals(orderStatusesExpected, orderStatusesActual);
    }

    @Test
    void testGetById() {
        Integer id = 1;
        orderStatus = new OrderStatus(id, null);
        Mockito.when(orderStatusRepository.getById(any())).thenReturn(orderStatus);
        OrderStatus orderStatusActual = orderStatusService.getById(id);

        Assert.assertEquals(id, orderStatusActual.getId());
    }
}