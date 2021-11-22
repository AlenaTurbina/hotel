package com.hotel.controller;

import com.hotel.dto.OrderBookingDTO;
import com.hotel.model.entity.*;
import com.hotel.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class OrderBookingController {
    private OrderBookingService orderBookingService;
    private UserService userService;
    private RoomService roomService;
    private OrderStatusService orderStatusService;
    private ClassApartmentService classApartmentService;
    private RoomTypeService roomTypeService;
    //temporary version userRole, orderStatusId for client and initial orderStatus
    private final Integer userId = 1;
    private final Integer orderStatusId = 1;


    @GetMapping(value = "/orders")
    public String orderBookings(Model model) {
        var orderBookings = orderBookingService.getAll();
        model.addAttribute("orderBookings", orderBookings);
        return "orders";
    }

    @GetMapping(value = "/createOrders")
    public String orderObjectForm(Model model) {
        model.addAttribute("orderBookingDTO", new OrderBookingDTO());
        var classApartments = classApartmentService.getAll();
        model.addAttribute("classApartments", classApartments);
        var roomTypes = roomTypeService.getAll();
        model.addAttribute("roomTypes", roomTypes);
        return "createOrders";
    }

    @PostMapping(value = "/createOrders")
    public String createOrderObject(@ModelAttribute OrderBookingDTO orderBookingDTO, Model model) {
        var room = roomService.checkRoom(orderBookingDTO.getRoomType(), orderBookingDTO.getClassApartment());
        var orderBooking = OrderBooking.builder()
                .date(LocalDate.now())
                .dateArrival(orderBookingDTO.getDateArrival())
                .dateDeparture(orderBookingDTO.getDateDeparture())
                .quantityPersons(orderBookingDTO.getQuantityPersons())
                .client(userService.getById(userId))
                .room(room)
                .orderStatus(orderStatusService.getById(orderStatusId))
                .build();
        orderBookingService.save(orderBooking);
        var orderBookings = orderBookingService.getAll();
        model.addAttribute("orderBookings", orderBookings);
        return "orders";
    }
}