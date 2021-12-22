package com.hotel.controller;

import com.hotel.dto.RoomTypeDTO;
import com.hotel.model.entity.RoomType;
import com.hotel.service.RoomTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/roomTypes")
@Controller
@AllArgsConstructor
public class RoomTypeController {
    private RoomTypeService roomTypeService;

    @GetMapping()
    public String roomTypes(Model model) {
        var roomTypes = roomTypeService.getAll();
        model.addAttribute("roomTypes", roomTypes);
        return "/admin/roomTypes";
    }

    @GetMapping("/create")
    public String createRoomTypesForm(Model model){
        var roomTypeDTO = new RoomTypeDTO();
        model.addAttribute("roomTypeDTO", roomTypeDTO);
        return "/admin/createRoomTypes";
    }

    @PostMapping("/create")
    public String saveRoomTypes(@ModelAttribute RoomTypeDTO roomTypeDTO){
        roomTypeService.saveByDTO(roomTypeDTO);
        return "redirect:/admin/roomTypes";
    }


    @GetMapping("/update/{id}")
    public String updateRoomTypesForm(@PathVariable ("id") Integer id, Model model) {
        var roomType = roomTypeService.getById(id);
        model.addAttribute("roomType", roomType);
        return "/admin/updateRoomTypes";
    }


    @PostMapping("/update")
    public  String updateRoomTypes(RoomType roomType) {
        roomTypeService.save(roomType);
        return "redirect:/admin/roomTypes";
    }







}
