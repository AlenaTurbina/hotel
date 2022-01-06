package com.hotel.controller;

import com.hotel.service.RoomKindService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class RoomKindController {
    private RoomKindService roomKindService;

    //List of roomKinds /GET/
    @GetMapping("/home/priceList")
    public String priceList(Model model) {
        var roomKinds = roomKindService.getAll();
        model.addAttribute("roomKinds", roomKinds);
        return "/home/priceList";
    }
}
