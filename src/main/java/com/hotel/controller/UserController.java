package com.hotel.controller;

import com.hotel.dto.UserDTO;
import com.hotel.model.entity.Role;
import com.hotel.model.entity.User;
import com.hotel.service.RoleService;
import com.hotel.service.UserService;
import com.hotel.service.UserStatusService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@Builder
public class UserController {
    private UserService userService;
    private RoleService roleService;
    private UserStatusService userStatusService;
    private final Integer clientRoleId = 2;
    private final Integer clientStatusId = 1;

    @GetMapping(value = "/admin/users")
    public String users(Model model) {
        var users = userService.getAll();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping(value = "/registration/createUsers")
    public String userObjectForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "createUsers";
    }


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Authentication authentication, Model model) {
        var email =  authentication.getName();
        var user = userService.getByEmail(email);
        model.addAttribute("user", user);
        return "/admin/adminPage";
    }




}



