package com.hotel.controller;

import com.hotel.dto.OrderBookingDTO;
import com.hotel.model.entity.OrderBooking;
import com.hotel.model.entity.RoomKind;
import com.hotel.service.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;

@Controller
@AllArgsConstructor
public class OrderBookingController {
    private OrderBookingService orderBookingService;
    private ClassApartmentService classApartmentService;
    private RoomTypeService roomTypeService;
    private OptionalService optionalService;
    private UserService userService;
    private RoomService roomService;
    private RoomKindService roomKindService;
    private OrderStatusService orderStatusService;


    @GetMapping(value = "/admin/orderBookings")
    public String orderBookings(Model model) {
        var orderBookings = orderBookingService.getAll();
        model.addAttribute("orderBookings", orderBookings);
        return "/admin/orderBookings";
    }


    @RequestMapping(value = "/client/orderForms", method = RequestMethod.GET)
    public String orderForm(Authentication authentication, Model model) {
        var email =  authentication.getName();
        var user = userService.getByEmail(email);
        model.addAttribute("user", user);
        model.addAttribute("orderBookingDTO", new OrderBookingDTO());
        var classApartments = classApartmentService.getAll();
        model.addAttribute("classApartments", classApartments);
        var roomTypes = roomTypeService.getAll();
        model.addAttribute("roomTypes", roomTypes);
        var optionals = optionalService.getAll();
        model.addAttribute("optionals", optionals);

        var orderBookings = orderBookingService.getAll();
        model.addAttribute("orderBookings", orderBookings);

        return "/client/orderForms";
    }


    @PostMapping(value = "/client/orderForms")
    public String createOrder(@ModelAttribute OrderBookingDTO orderBookingDTO, Model model) {
        var orderBooking = orderBookingService.save1(orderBookingDTO);
        if (orderBooking != null) {
            model.addAttribute("orderBooking", orderBooking);
            return "/client/orderResultInvoice";
        } else {
            model.addAttribute("orderBookingDTO", orderBookingDTO);
            var classApartments = classApartmentService.getAll();
            model.addAttribute("classApartments", classApartments);
            var roomTypes = roomTypeService.getAll();
            model.addAttribute("roomTypes", roomTypes);
            return "/client/orderResultRefusal";
        }
    }

    @GetMapping(value = "/freeRoomForms")
    public String freeRoomsForm(Model model) {
        model.addAttribute("orderBookingDTO", new OrderBookingDTO());
        return "/greeting/freeRoomForms";
    }


    @PostMapping(value = "/freeRoomForms")
    public String showFreeRooms(@ModelAttribute OrderBookingDTO orderBookingDTO, Model model) {
        var freeRooms = orderBookingService.getFreeRooms(orderBookingDTO);
        if (freeRooms != null) {
            model.addAttribute("orderBookingDTO", orderBookingDTO);
            model.addAttribute("freeRooms", freeRooms);
            var roomKinds = roomKindService.getAll();
            model.addAttribute("roomKinds", roomKinds);
            return "/greeting/freeRoomsClient";
        } else {
            model.addAttribute("orderBookingDTO", orderBookingDTO);
            return "/greeting/noFreeRooms";
        }
    }

    @GetMapping(value = "/admin/freeRoomFormsAdmin")
    public String freeRoomsFormAdmin(Model model) {
        model.addAttribute("orderBookingDTO", new OrderBookingDTO());
        return "/admin/freeRoomFormsAdmin";
    }

    @PostMapping(value = "/admin/freeRoomFormsAdmin")
    public String showFreeRoomsAdmin(@ModelAttribute OrderBookingDTO orderBookingDTO, Model model) {
        var freeRooms = orderBookingService.getFreeRooms(orderBookingDTO);
        if (freeRooms != null) {
            model.addAttribute("orderBookingDTO", orderBookingDTO);
            model.addAttribute("freeRooms", freeRooms);
            var roomKinds = roomKindService.getAll();
            model.addAttribute("roomKinds", roomKinds);
            return "/admin/freeRoomsAdmin";
        } else {
            model.addAttribute("orderBookingDTO", orderBookingDTO);
            return "/admin/noFreeRooms";
        }
    }



    @GetMapping("/admin/orderBookings/update/{id}")
    public String updateOrderBookingForm(@PathVariable("id") Integer id, Model model) {
        var orderBooking = orderBookingService.getById(id);
        model.addAttribute("orderBooking", orderBooking);
        var rooms = roomService.getAll();
        model.addAttribute("rooms", rooms);
        var orderStatuses = orderStatusService.getAll();
        model.addAttribute("orderStatuses", orderStatuses);
        var optionals = optionalService.getAll();
        model.addAttribute("optionals", optionals);
    return "/admin/updateOrderBookings";
    }


    @PostMapping("/admin/orderBookings/update")
    public String updateOrderBooking(OrderBooking orderBooking){
        orderBookingService.update(orderBooking);
        return "redirect:/admin/orderBookings";
    }





}


