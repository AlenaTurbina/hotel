package com.hotel.controller;

import com.hotel.dto.RoomTypeDTO;
import com.hotel.model.entity.RoomType;
import com.hotel.service.RoomTypeService;
import com.hotel.validator.RoomTypeUpdateValidator;
import com.hotel.validator.RoomTypeValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/roomTypes")
@Controller
@AllArgsConstructor
public class RoomTypeController {
    private RoomTypeService roomTypeService;
    private RoomTypeUpdateValidator roomTypeUpdateValidator;
    private RoomTypeValidator roomTypeValidator;

    //List of RoomTypes /GET/
    @GetMapping()
    public String roomTypes(Model model) {
        var roomTypes = roomTypeService.getAll();
        model.addAttribute("roomTypes", roomTypes);
        return "/admin/roomTypes";
    }

    //Create new RoomType /GET, POST/
    @GetMapping("/create")
    public String createRoomTypesForm(Model model) {
        var roomTypeDTO = new RoomTypeDTO();
        model.addAttribute("roomTypeDTO", roomTypeDTO);
        return "/admin/createRoomTypes";
    }

    @PostMapping("/create")
    public String saveRoomTypes(@ModelAttribute RoomTypeDTO roomTypeDTO, BindingResult bindingResult) {
        roomTypeValidator.validate(roomTypeDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/admin/createRoomTypes";
        }
        roomTypeService.save(roomTypeDTO);
        return "redirect:/admin/roomTypes";
    }

    //Update RoomType /GET, POST/
    @GetMapping("/update/{id}")
    public String updateRoomTypesForm(@PathVariable("id") Integer id, Model model) {
        var roomType = roomTypeService.getById(id);
        model.addAttribute("roomType", roomType);
        return "/admin/updateRoomTypes";
    }

    @PostMapping("/update")
    public String updateRoomTypes(RoomType roomType, BindingResult bindingResult) {
        roomTypeUpdateValidator.validate(roomType, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/admin/updateRoomTypes";
        }
        roomTypeService.update(roomType);
        return "redirect:/admin/roomTypes";
    }

}
