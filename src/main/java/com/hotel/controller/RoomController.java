package com.hotel.controller;

import com.hotel.dto.RoomDTO;
import com.hotel.model.entity.Room;
import com.hotel.service.*;
import com.hotel.validator.RoomUpdateValidator;
import com.hotel.validator.RoomValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RoomController {
    private RoomService roomService;
    private ClassApartmentService classApartmentService;
    private RoomTypeService roomTypeService;
    private RoomKindService roomKindService;
    private OptionalService optionalService;
    private RoomValidator roomValidator;
    private RoomUpdateValidator roomUpdateValidator;

    //List of rooms /GET/
    @GetMapping("/admin/rooms")
    public String rooms(Model model) {
        var rooms = roomService.getAll();
        model.addAttribute("rooms", rooms);
        return "/admin/rooms";
    }

    //List of unique roomKinds from rooms and all optionals for price-list /GET/
    @GetMapping("/home/priceList")
    public String priceList(Model model) {
        var roomKinds = roomService.getListUniqueRoomKindsFromRooms();
        model.addAttribute("roomKinds", roomKinds);
        var optionals = optionalService.getAll();
        model.addAttribute("optionals", optionals);
        return "/home/priceList";
    }

    //List of unique roomKinds from rooms for roomCards /GET/
    @GetMapping("/home/roomCards")
    public String showRoomCards(Model model) {
        var roomKinds = roomService.getListUniqueRoomKindsFromRooms();
        model.addAttribute("roomKinds", roomKinds);
        var classApartments = classApartmentService.getAll();
        model.addAttribute("classApartments", classApartments);
        var roomTypes = roomTypeService.getAll();
        model.addAttribute("roomTypes", roomTypes);
        return "/home/roomCards";
    }

    //Create new room /GET, POST/
    @GetMapping("/admin/rooms/create")
    public String createRoomForm(Model model) {
        var roomDTO = new RoomDTO();
        model.addAttribute("roomDTO", roomDTO);
        showRoomTypeAndClassApartmentAndRoomKindModel(model);
        return "/admin/createRooms";
    }

    @PostMapping("/admin/rooms/create")
    public String saveRooms(@ModelAttribute RoomDTO roomDTO, BindingResult bindingResult, Model model) {
        roomValidator.validate(roomDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            showRoomTypeAndClassApartmentAndRoomKindModel(model);
            return "/admin/createRooms";
        }
        roomService.save(roomDTO);
        return "redirect:/admin/rooms";
    }

    //Update new room /GET, POST/
    @GetMapping("/admin/rooms/update/{id}")
    public String updateRoomsForm(@PathVariable("id") Integer id, Model model) {
        var room = roomService.getById(id);
        model.addAttribute("room", room);
        showRoomTypeAndClassApartmentAndRoomKindModel(model);
        return "/admin/updateRooms";
    }

    @PostMapping("/admin/rooms/update")
    public String updateRooms(Room room, BindingResult bindingResult, Model model) {
        roomUpdateValidator.validate(room, bindingResult);
        if (bindingResult.hasErrors()) {
            showRoomTypeAndClassApartmentAndRoomKindModel(model);
            return "/admin/updateRooms";
        }
        roomService.update(room.getId(), room.getName(), room.getRoomKind());
        return "redirect:/admin/rooms";
    }

    //Method for getting roomTypes, classApartments and roomKinds
    private void showRoomTypeAndClassApartmentAndRoomKindModel(Model model) {
        var classApartments = classApartmentService.getAll();
        model.addAttribute("classApartments", classApartments);
        var roomTypes = roomTypeService.getAll();
        model.addAttribute("roomTypes", roomTypes);
        var roomKinds = roomKindService.getAll();
        model.addAttribute("roomKinds", roomKinds);
    }

}
