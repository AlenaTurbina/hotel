package com.hotel.controller;

import com.hotel.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class RoomController {
    private RoomService roomService;

    //List of Rooms /GET/
    @GetMapping(value = "/admin/rooms")
    public String rooms(Model model) {
        var rooms = roomService.getAll();
        model.addAttribute("rooms", rooms);
        return "/admin/rooms";
    }

}
