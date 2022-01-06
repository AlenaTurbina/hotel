package com.hotel.controller;

import com.hotel.model.entity.User;
import com.hotel.service.RoleService;
import com.hotel.service.UserService;
import com.hotel.service.UserStatusService;
import com.hotel.validator.UserUpdateValidator;
import lombok.AllArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin")
@Controller
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private RoleService roleService;
    private UserStatusService userStatusService;
    private UserUpdateValidator userUpdateValidator;

    //List of users /GET/
    @GetMapping("/users")
    public String users(Model model) {
        var users = userService.getAll();
        model.addAttribute("users", users);
        return "admin/users";
    }

    //Admin page /GET/
    @GetMapping()
    public String admin(Authentication authentication, Model model) {
        var user = userService.findLoggedUser(authentication);
        model.addAttribute("user", user);
        return "/admin/adminPage";
    }

    //Update user /GET, POST/
    @GetMapping("/users/update/{id}")
    public String updateUserForm(@PathVariable("id") Integer id, Model model) {
        var user = userService.getById(id);
        model.addAttribute("user", user);
        var roles = roleService.getAll();
        model.addAttribute("roles", roles);
        var userStatuses = userStatusService.getAll();
        model.addAttribute("userStatuses", userStatuses);
        return "/admin/updateUsers";
    }

    @PostMapping("/users/update")
    public String updateUser(User user, BindingResult bindingResult, Model model) {
        userUpdateValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            var roles = roleService.getAll();
            model.addAttribute("roles", roles);
            var userStatuses = userStatusService.getAll();
            model.addAttribute("userStatuses", userStatuses);
            return "/admin/updateUsers";
        }
        userService.update(user);
        return "redirect:/admin/users";
    }
}



