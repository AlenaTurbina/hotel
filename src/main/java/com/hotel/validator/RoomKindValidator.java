package com.hotel.validator;

import com.hotel.dto.RoomKindDTO;
import com.hotel.service.RoomKindService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class RoomKindValidator implements Validator {

    private RoomKindService roomKindService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoomKindDTO roomKindDTO = (RoomKindDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomType", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "classApartment", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomPrice", "validation.required");

        if (roomKindDTO.getRoomPrice() <= 0) {
            errors.rejectValue("roomPrice", "validation.field.positive");
        }

        if (roomKindService.getRoomKindByRoomTypeAndClassApartmentID(roomKindDTO.getRoomType(), roomKindDTO.getClassApartment()) != null) {
            errors.rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
        }


    }

}
