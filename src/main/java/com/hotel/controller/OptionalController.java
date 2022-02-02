package com.hotel.controller;

import com.hotel.dto.OptionalDTO;
import com.hotel.model.entity.Optional;
import com.hotel.service.OptionalService;
import com.hotel.validator.OptionalUpdateValidator;
import com.hotel.validator.OptionalValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/optionals")
@Controller
@AllArgsConstructor
public class OptionalController {
    private OptionalService optionalService;
    private OptionalUpdateValidator optionalUpdateValidator;
    private OptionalValidator optionalValidator;

    //List of Optionals /GET/
    @GetMapping()
    private String optionals(Model model) {
        var optionals = optionalService.getAll();
        model.addAttribute("optionals", optionals);
        return "/admin/optionals";
    }

    //Create optional /GET, POST/
    @GetMapping("/create")
    private String createOptionalForm(Model model){
        var optionalDTO = new OptionalDTO();
        model.addAttribute("optionalDTO", optionalDTO);
        return "/admin/createOptionals";
    }

    @PostMapping("/create")
    private String createOptional(@ModelAttribute OptionalDTO optionalDTO, BindingResult bindingResult){
        optionalValidator.validate(optionalDTO, bindingResult);
        if(bindingResult.hasErrors()){
            return "/admin/createOptionals";
        }
        optionalService.save(optionalDTO);
        return "redirect:/admin/optionals";
    }

    //Update optional /GET, POST/
    @GetMapping("/update/{id}")
    public String updateOptionalsForm(@PathVariable("id") Integer id, Model model) {
        var optional = optionalService.getById(id);
        model.addAttribute("optional", optional);
        return "/admin/updateOptionals";
    }

    @PostMapping("/update")
    public String updateOptionals(Optional optional, BindingResult bindingResult){
        optionalUpdateValidator.validate(optional, bindingResult);
        if(bindingResult.hasErrors()){
            return "/admin/updateOptionals";
        }
        optionalService.update(optional);
        return "redirect:/admin/optionals";
    }

}
