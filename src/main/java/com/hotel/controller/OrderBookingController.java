package com.hotel.controller;

import com.hotel.dto.OrderBookingDTO;
import com.hotel.model.entity.OrderBooking;
import com.hotel.service.*;
import com.hotel.service.impl.EmailSenderServiceImpl;
import com.hotel.validator.DateBookingValidator;
import com.hotel.validator.OrderBookingValidator;
import com.hotel.validator.OrderUpdateValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

import static com.hotel.utilit.Constant.*;


@Controller
@Slf4j
@AllArgsConstructor
public class OrderBookingController {
    private OrderBookingService orderBookingService;
    private ClassApartmentService classApartmentService;
    private RoomTypeService roomTypeService;
    private RoomService roomService;
    private OptionalService optionalService;
    private UserService userService;
    private OrderStatusService orderStatusService;
    private OrderBookingValidator orderBookingValidator;
    private DateBookingValidator dateBookingValidator;
    private OrderUpdateValidator orderUpdateValidator;
    private EmailSenderServiceImpl emailSenderService;


    //List of orderBookings with pagination /GET/
    @GetMapping(value = "/admin/orderBookings")
    public String orderBookings(Model model) {
       return findPaginated(1, model);
    }

    //Pagination for all orderBookings
    @GetMapping("/admin/orderBookings/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") Integer pageNo, Model model) {
        Integer pageSize = MAX_ITEMS_ON_PAGE;

        Page<OrderBooking> page = orderBookingService.findPaginated(pageNo, pageSize);
        List<OrderBooking> orderBookings = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("orderBookings", orderBookings);
        return "/admin/orderBookings";
    }

    //Booking - orderBooking form with validation /GET, POST/
    @RequestMapping(value = "/client/orderForms", method = RequestMethod.GET)
    public String orderForm(Authentication authentication, Model model) {
        model.addAttribute("orderBookingDTO", new OrderBookingDTO());
        showAuthenticatedUserAndOrderForm(authentication, model);
        return "/client/orderForms";
    }

    @PostMapping(value = "/client/orderForms")
    public String createOrder(@ModelAttribute OrderBookingDTO orderBookingDTO, BindingResult bindingResult,
                              Authentication authentication, Model model)  {
        orderBookingValidator.validate(orderBookingDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            showAuthenticatedUserAndOrderForm(authentication, model);
            return "client/orderForms";
        }
        var orderBooking = orderBookingService.save(orderBookingDTO);
        log.info("New order booking");
        if (orderBooking != null) {
            log.info("New invoice");
            try {
                emailSenderService.sendHtmlEmailInvoice(orderBooking.getClient().getEmail(), EMAIL_BOOKING_SUBJECT, orderBooking, "/mails/mailInvoice.html");
                log.info("New email was send");
            } catch (MessagingException e) {
                log.error("Exception: ", e);
            }
            model.addAttribute("orderBooking", orderBooking);
            return "/client/orderResultInvoice";
        } else {
            log.info("No suitable room for booking");
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
        var roomKinds = roomService.getListUniqueRoomKindsFromRooms();
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
        var roomKinds = roomService.getListUniqueRoomKindsFromRooms();
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

    //List of orderBookings for client's history with pagination/GET/
    @GetMapping(value = "/client/orderBookings")
    public String orderBookingsUser(Model model, Authentication authentication) {
        return findPaginatedForUser(1, model, authentication);
    }

    //Pagination for client's history
    @GetMapping("/client/orderBookings/page/{pageNo}")
    public String findPaginatedForUser(@PathVariable(value = "pageNo") Integer pageNo, Model model, Authentication authentication) {
        Integer pageSize = MAX_ITEMS_ON_PAGE;

        List<OrderBooking> page = orderBookingService.findPaginatedAllByUser(pageNo, pageSize, authentication);
        List<OrderBooking> allPages = orderBookingService.findAllByUser(authentication);
        int totalPages = allPages.size()/pageSize + 1;

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", allPages.size());
        model.addAttribute("orderBookings", page);
        model.addAttribute("user", userService.findLoggedUser(authentication));
        return "/client/orderUsers";
    }

    //Method for getting list of authenticated client & orderBooking form
     private void showAuthenticatedUserAndOrderForm(Authentication authentication, Model model) {
        var user = userService.findLoggedUser(authentication);
        model.addAttribute("user", user);
        var classApartments = roomService.getListUniqueClassApartmentsFromRooms();
        model.addAttribute("classApartments", classApartments);
        var roomTypes = roomService.getListUniqueRoomTypesFromRooms();
        model.addAttribute("roomTypes", roomTypes);
        var optionals = optionalService.getAll();
        model.addAttribute("optionals", optionals);
    }

}


