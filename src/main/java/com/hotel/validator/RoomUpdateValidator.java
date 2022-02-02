package com.hotel.validator;

import com.hotel.model.entity.Room;
import com.hotel.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class RoomUpdateValidator implements Validator {
    RoomService roomService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Room room = (Room) target;

        Integer checkID = checkRoom(room);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomKind", "validation.required");

        if (checkID != null && checkID != room.getId()) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }

    }

    public Integer checkRoom(Room room) {
        Room findRoom = roomService.getByName(room.getName());
        if(findRoom !=null){
            return findRoom.getId();
        } return null;
    }

}
