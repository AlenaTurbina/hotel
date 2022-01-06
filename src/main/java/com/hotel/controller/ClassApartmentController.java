package com.hotel.controller;

import com.hotel.dto.ClassApartmentDTO;
import com.hotel.model.entity.ClassApartment;
import com.hotel.service.ClassApartmentService;
import com.hotel.validator.ClassApartmentUpdateValidator;
import com.hotel.validator.ClassApartmentValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/classApartments")
@Controller
@AllArgsConstructor
public class ClassApartmentController {
    private ClassApartmentService classApartmentService;
    private ClassApartmentUpdateValidator classApartmentUpdateValidator;
    private ClassApartmentValidator classApartmentValidator;

    //List of ClassApartment /GET/
    @GetMapping()
    public String classApartments(Model model) {
        var classApartments = classApartmentService.getAll();
        model.addAttribute("classApartments", classApartments);
        return "/admin/classApartments";
    }

    //Create new classApartment /GET, POST/
    @GetMapping("/create")
    public String createClassApartmentsForm(Model model) {
        var classApartmentDTO = new ClassApartmentDTO();
        model.addAttribute("classApartmentDTO", classApartmentDTO);
        return "/admin/createClassApartments";
    }

    @PostMapping("/create")
    public String saveClassApartments(@ModelAttribute ClassApartmentDTO classApartmentDTO, BindingResult bindingResult) {
        classApartmentValidator.validate(classApartmentDTO, bindingResult);
        if (bindingResult.hasErrors()){
            return "/admin/createClassApartments";
        }
        classApartmentService.save(classApartmentDTO);
        return "redirect:/admin/classApartments";
    }


    //Update classApartment /GET, POST/
    @GetMapping("/update/{id}")
    public String updateClassApartmentsForm(@PathVariable("id") Integer id, Model model) {
        var classApartment = classApartmentService.getById(id);
        model.addAttribute("classApartment", classApartment);
        return "/admin/updateClassApartments";
    }

    @PostMapping("/update")
    public String updateClassApartments(ClassApartment classApartment, BindingResult bindingResult) {
        classApartmentUpdateValidator.validate(classApartment, bindingResult);
        if (bindingResult.hasErrors()){
            return "/admin/updateClassApartments";
        }

        classApartmentService.update(classApartment);
        return "redirect:/admin/classApartments";
    }

}
