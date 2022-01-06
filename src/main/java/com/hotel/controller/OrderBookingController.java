package com.hotel.controller;

import com.hotel.dto.OrderBookingDTO;
import com.hotel.model.entity.OrderBooking;
import com.hotel.service.*;
import com.hotel.validator.DateBookingValidator;
import com.hotel.validator.OrderBookingValidator;
import com.hotel.validator.OrderUpdateValidator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
public class OrderBookingController {
    private OrderBookingService orderBookingService;
    private ClassApartmentService classApartmentService;
    private RoomTypeService roomTypeService;
    private OptionalService optionalService;
    private UserService userService;
    private RoomKindService roomKindService;
    private OrderStatusService orderStatusService;
    private OrderBookingValidator orderBookingValidator;
    private DateBookingValidator dateBookingValidator;
    private OrderUpdateValidator orderUpdateValidator;

    private static final Logger logger = LoggerFactory.getLogger(OrderBookingController.class);

    //List of orderBookings /GET/
    @GetMapping(value = "/admin/orderBookings")
    public String orderBookings(Model model) {
        var orderBookings = orderBookingService.getAll();
        model.addAttribute("orderBookings", orderBookings);
        return "/admin/orderBookings";
    }

    //Booking - list of authenticated client orders & orderBooking form with validation /GET, POST/
    @RequestMapping(value = "/client/orderForms", method = RequestMethod.GET)
    public String orderForm(Authentication authentication, Model model) {
        model.addAttribute("orderBookingDTO", new OrderBookingDTO());
        showAuthenticatedUserOrdersAndOrderForm(authentication, model);
        return "/client/orderForms";
    }

    @PostMapping(value = "/client/orderForms")
    public String createOrder(@ModelAttribute OrderBookingDTO orderBookingDTO, BindingResult bindingResult,
                              Authentication authentication, Model model) {
        orderBookingValidator.validate(orderBookingDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            showAuthenticatedUserOrdersAndOrderForm(authentication, model);
            return "/client/orderForms";
        }
        var orderBooking = orderBookingService.save(orderBookingDTO);
        if (orderBooking != null) {
            model.addAttribute("orderBooking", orderBooking);
            logger.info("New order booking");
            logger.info("New invoice");
            return "/client/orderResultInvoice";

        } else {
            logger.info("No suitable room for booking");
            model.addAttribute("orderBookingDTO", orderBookingDTO);
            var classApartments = classApartmentService.getAll();
            model.addAttribute("classApartments", classApartments);
            var roomTypes = roomTypeService.getAll();
            model.addAttribute("roomTypes", roomTypes);
            return "/client/orderResultRefusal";
        }
    }

    //Getting list of free rooms on selected dates for clients /GET, POST/
    @GetMapping(value = "/home/freeRoomForms")
    public String freeRoomsForm(Model model) {
        model.addAttribute("orderBookingDTO", new OrderBookingDTO());
        return "/home/freeRoomForms";
    }

    @PostMapping(value = "/home/freeRoomForms")
    public String showFreeRooms(@ModelAttribute OrderBookingDTO orderBookingDTO, BindingResult bindingResult, Model model) {
        dateBookingValidator.validate(orderBookingDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/home/freeRoomForms";
        }
        var freeRooms = orderBookingService.getFreeRooms(orderBookingDTO);
        model.addAttribute("orderBookingDTO", orderBookingDTO);
        model.addAttribute("freeRooms", freeRooms);
        var roomKinds = roomKindService.getAll();
        model.addAttribute("roomKinds", roomKinds);
        return "/home/freeRoomsClient";
    }

    //Getting list of free rooms on selected dates for admins /GET, POST/
    @GetMapping(value = "/admin/freeRoomFormsAdmin")
    public String freeRoomsFormAdmin(Model model) {
        model.addAttribute("orderBookingDTO", new OrderBookingDTO());
        return "/admin/freeRoomFormsAdmin";
    }

    @PostMapping(value = "/admin/freeRoomFormsAdmin")
    public String showFreeRoomsAdmin(@ModelAttribute OrderBookingDTO orderBookingDTO, BindingResult bindingResult, Model model) {
        dateBookingValidator.validate(orderBookingDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/admin/freeRoomFormsAdmin";
        }
        var freeRooms = orderBookingService.getFreeRooms(orderBookingDTO);
        model.addAttribute("orderBookingDTO", orderBookingDTO);
        model.addAttribute("freeRooms", freeRooms);
        var roomKinds = roomKindService.getAll();
        model.addAttribute("roomKinds", roomKinds);
        return "/admin/freeRoomsAdmin";
    }

    //Update orderBooking /GET, POST/
    @GetMapping("/admin/orderBookings/update/{id}")
    public String updateOrderBookingForm(@PathVariable("id") Integer id, Model model) {
        var orderBooking = orderBookingService.getById(id);
        model.addAttribute("orderBooking", orderBooking);
        var rooms = orderBookingService.getListFreeRoomsOnOrderBookingDates(orderBooking);
        model.addAttribute("rooms", rooms);
        var orderStatuses = orderStatusService.getAll();
        model.addAttribute("orderStatuses", orderStatuses);
        var optionals = optionalService.getAll();
        model.addAttribute("optionals", optionals);
        return "/admin/updateOrderBookings";
    }

    @PostMapping("/admin/orderBookings/update")
    public String updateOrderBooking(OrderBooking orderBooking, BindingResult bindingResult, Model model) {
        orderUpdateValidator.validate(orderBooking, bindingResult);
        if (bindingResult.hasErrors()) {
            var rooms = orderBookingService.getListFreeRoomsOnOrderBookingDates(orderBooking);
            model.addAttribute("rooms", rooms);
            var orderStatuses = orderStatusService.getAll();
            model.addAttribute("orderStatuses", orderStatuses);
            return "/admin/updateOrderBookings";
        }
        orderBookingService.update(orderBooking);
        return "redirect:/admin/orderBookings";
    }


    //Method for getting list of authenticated client orders & orderBooking form
    private void showAuthenticatedUserOrdersAndOrderForm(Authentication authentication, Model model) {
        var user = userService.findLoggedUser(authentication);
        model.addAttribute("user", user);
        var classApartments = classApartmentService.getAll();
        model.addAttribute("classApartments", classApartments);
        var roomTypes = roomTypeService.getAll();
        model.addAttribute("roomTypes", roomTypes);
        var optionals = optionalService.getAll();
        model.addAttribute("optionals", optionals);
        var orderBookings = orderBookingService.getAll();
        model.addAttribute("orderBookings", orderBookings);
    }

}


