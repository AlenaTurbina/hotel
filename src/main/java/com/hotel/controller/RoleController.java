package com.hotel.controller;

import com.hotel.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class RoleController {
    private RoleService roleService;

    //List of Roles /GET/
    @GetMapping(value = "/admin/roles")
    public String roles(Model model) {
        var roles = roleService.getAll();
        model.addAttribute("roles", roles);
        return "/admin/roles";
    }
}
