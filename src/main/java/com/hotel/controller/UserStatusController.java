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

    //List of UserStatuses /GET/
    @GetMapping(value = "/admin/userStatuses")
    public String userStatus(Model model) {
        var userStatuses = userStatusService.getAll();
        model.addAttribute("userStatuses", userStatuses);
        return "/admin/userStatuses";
    }
}
