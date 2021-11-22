package com.hotel.controller;

import com.hotel.service.RoomTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class RoomTypeController {
    private RoomTypeService roomTypeService;

    @GetMapping(value = "/roomTypes")
    public String roomTypes(Model model) {
        var roomTypes = roomTypeService.getAll();
        model.addAttribute("roomTypes", roomTypes);
        return "roomTypes";
    }

}
