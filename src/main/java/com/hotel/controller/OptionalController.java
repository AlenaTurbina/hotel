package com.hotel.controller;

import com.hotel.service.OptionalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class OptionalController {
    private OptionalService optionalService;

    //creating a list of objects - class "Optionals" (method GET)
    @GetMapping(value = "/admin/optionals")
    private String optionals(Model model) {
        var optionals = optionalService.getAll();
        model.addAttribute("optionals", optionals);
        return "/admin/optionals";
    }
}
