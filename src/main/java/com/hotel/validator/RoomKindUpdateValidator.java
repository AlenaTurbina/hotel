package com.hotel.validator;

import com.hotel.model.entity.RoomKind;
import com.hotel.service.RoomKindService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class RoomKindUpdateValidator implements Validator {

    private RoomKindService roomKindService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoomKind roomKind = (RoomKind) target;

        Integer falseID = roomKindService.getRoomKindByRoomTypeAndClassApartmentID(roomKind.getRoomType().getId(), roomKind.getClassApartment().getId());

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomType", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "classApartment", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomPrice", "validation.required");

        if (roomKind.getRoomPrice() <= 0) {
            errors.rejectValue("roomPrice", "validation.field.positive");
        }

        if (falseID != null && falseID != roomKind.getId()) {
            errors.rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
        }


    }

}
