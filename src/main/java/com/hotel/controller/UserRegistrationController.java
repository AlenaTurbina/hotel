package com.hotel.controller;

import com.hotel.dto.UserDTO;
import com.hotel.service.UserService;
import com.hotel.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/registration")
@Controller
@AllArgsConstructor
public class UserRegistrationController {
    private UserService userService;
    private UserValidator userValidator;


    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "/account/registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult) {
        userValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/account/registration";
        }
        userService.saveAndReturn(userDTO);
        return "redirect:/registration?success";
    }

}
