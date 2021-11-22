package com.hotel.controller;

import com.hotel.service.ClassApartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ClassApartmentController {
    private ClassApartmentService classApartmentService;

    @GetMapping(value = "/classApartments")
    public String classApartments(Model model) {
        var classApartments = classApartmentService.getAll();
        model.addAttribute("classApartments", classApartments);
        return "classApartments";
    }
}
