package com.hotel.controller;

import com.hotel.service.OrderStatusService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class OrderStatusController {
    private OrderStatusService orderStatusService;

    @GetMapping(value = "/admin/orderStatuses")
    public String orderStatuses(Model model) {
        var orderStatuses = orderStatusService.getAll();
        model.addAttribute("orderStatuses", orderStatuses);
        return "orderStatuses";
    }
}
