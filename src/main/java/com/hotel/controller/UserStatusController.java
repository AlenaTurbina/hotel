package com.hotel.controller;

import com.hotel.service.UserStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class UserStatusController {
    private UserStatusService userStatusService;

    @GetMapping(value = "/userStatuses")
    public String userStatus(Model model) {
        var userStatuses = userStatusService.getAll();
        model.addAttribute("userStatuses", userStatuses);
        return "userStatuses";
    }
}
