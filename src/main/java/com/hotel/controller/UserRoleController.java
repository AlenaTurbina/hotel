package com.hotel.controller;

import com.hotel.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class UserRoleController {
    private UserRoleService userRoleService;

    @GetMapping(value = "/userRoles")
    public String userRoles(Model model) {
        var userRoles = userRoleService.getAll();
        model.addAttribute("userRoles", userRoles);
        return "userRoles";
    }
}
