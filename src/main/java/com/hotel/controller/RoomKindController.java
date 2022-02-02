package com.hotel.controller;

import com.hotel.dto.RoomKindDTO;
import com.hotel.model.entity.RoomKind;
import com.hotel.service.*;
import com.hotel.validator.RoomKindUpdateValidator;
import com.hotel.validator.RoomKindValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@AllArgsConstructor
public class RoomKindController {
    private RoomKindService roomKindService;
    private RoomTypeService roomTypeService;
    private ClassApartmentService classApartmentService;
    private RoomKindValidator roomKindValidator;
    private RoomKindUpdateValidator roomKindUpdateValidator;


    //List of roomKinds /GET/
    @GetMapping("/admin/roomKinds")
    public String roomKinds(Model model) {
        var roomKinds = roomKindService.getAll();
        model.addAttribute("roomKinds", roomKinds);
        return "/admin/roomKinds";
    }

    //Create roomKinds /GET, POST/
    @GetMapping("/admin/roomKinds/create")
    public String createRoomKindForm(Model model) {
        var roomKindDTO = new RoomKindDTO();
        model.addAttribute("roomKindDTO", roomKindDTO);
        showRoomTypeAndClassApartmentModel(model);
        return "/admin/createRoomKinds";
    }

    @PostMapping("/admin/roomKinds/create")
    public String saveRoomKind(@ModelAttribute RoomKindDTO roomKindDTO, BindingResult bindingResult, Model model) {
        roomKindValidator.validate(roomKindDTO, bindingResult);
        if (bindingResult.hasErrors()){
            showRoomTypeAndClassApartmentModel(model);
            return "/admin/createRoomKinds";
        }
        roomKindService.save(roomKindDTO);
        return "redirect:/admin/roomKinds";
    }

    //Update roomKinds /GET, POST/
    @GetMapping("/admin/roomKinds/update/{id}")
    public String updateRoomKindForm (@PathVariable("id") Integer id, Model model){
        var roomKind = roomKindService.getById(id);
        model.addAttribute("roomKind", roomKind);
        showRoomTypeAndClassApartmentModel(model);
        return "/admin/updateRoomKinds";
    }

    @PostMapping("/admin/roomKinds/update")
    public String updateRoomKind(RoomKind roomKind, BindingResult bindingResult, Model model) {
        roomKindUpdateValidator.validate(roomKind, bindingResult);
        if (bindingResult.hasErrors()){
            showRoomTypeAndClassApartmentModel(model);
            return "/admin/updateRoomKinds";
        }
        roomKindService.update(roomKind.getId(), roomKind.getRoomType(), roomKind.getClassApartment(), roomKind.getRoomPrice());
        return "redirect:/admin/roomKinds";
    }


    //Method for getting roomTypes and classApartments
    private void showRoomTypeAndClassApartmentModel(Model model) {
        var classApartments = classApartmentService.getAll();
        model.addAttribute("classApartments", classApartments);
        var roomTypes = roomTypeService.getAll();
        model.addAttribute("roomTypes", roomTypes);
    }

}

