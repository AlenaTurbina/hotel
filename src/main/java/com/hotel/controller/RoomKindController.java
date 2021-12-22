package com.hotel.controller;


import com.hotel.model.entity.RoomKind;
import com.hotel.service.RoomKindService;
import com.hotel.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class RoomKindController {
    private RoomKindService roomKindService;

    @GetMapping("/priceList")
    public String priceList(Model model) {
        var roomKinds = roomKindService.getAll();
        model.addAttribute("roomKinds", roomKinds);
        return "/greeting/priceList";
    }
}
