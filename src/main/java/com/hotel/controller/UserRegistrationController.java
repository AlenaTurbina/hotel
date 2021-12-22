package com.hotel.controller;


import com.hotel.dto.UserDTO;
import com.hotel.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RequestMapping("/registration")
@Controller
@AllArgsConstructor
@Builder
public class UserRegistrationController {
    private UserService userService;


    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "registration";
    }


//    @PostMapping
//    public String registerUserAccount(@ModelAttribute("userDTO") UserDTO userDTO) {
//        userService.save1(userDTO);
//        return "redirect:/registration?success";
//    }


    @PostMapping
    public String registerUserAccount(@Valid @ModelAttribute("userDTO")  UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }
        userService.save1(userDTO);
        return "redirect:/registration?success";
    }





    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String currentUserName(Authentication authentication, Model model) {
        var email = authentication.getName();
        var user = userService.getByEmail(email);
        model.addAttribute("user", user);
        return "name";

    }


}
