package com.hotel.controller;

import com.hotel.dto.ClassApartmentDTO;
import com.hotel.model.entity.ClassApartment;
import com.hotel.service.ClassApartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/classApartments")
@Controller
@AllArgsConstructor
public class ClassApartmentController {
    private ClassApartmentService classApartmentService;


//creating a list of objects - class "ClassApartment" (method GET)

    @GetMapping()
    public String classApartments(Model model) {
        var classApartments = classApartmentService.getAll();
        model.addAttribute("classApartments", classApartments);
        return "/admin/classApartments";
    }


//create new object of Class Apartment (method GET, method POST)

    @GetMapping("/create")
    public String createClassApartmentsForm(Model model) {
        var classApartmentDTO = new ClassApartmentDTO();
        model.addAttribute("classApartmentDTO", classApartmentDTO);
        return "/admin/createClassApartments";
    }

    @PostMapping("/create")
    public String saveClassApartments(@ModelAttribute ClassApartmentDTO classApartmentDTO) {
        classApartmentService.saveByDTO(classApartmentDTO);
        return "redirect:/admin/classApartments";
    }


//update object of Class Apartment (method GET, method POST)

    @GetMapping("/update/{id}")
    public String updateClassApartmentsForm(@PathVariable("id") Integer id, Model model) {
        var classApartment = classApartmentService.getById(id);
        model.addAttribute("classApartment", classApartment);
        return "/admin/updateClassApartments";
    }

    @PostMapping("/update")
    public String updateClassApartments(ClassApartment classApartment) {
        classApartmentService.save(classApartment);
        return "redirect:/admin/classApartments";
    }

}
