package com.hotel.validator;

import com.hotel.dto.RoomDTO;
import com.hotel.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class RoomValidator implements Validator {
    private RoomService roomService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoomDTO roomDTO = (RoomDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomKind", "validation.required");

        if (roomService.getByName(roomDTO.getName()) !=null) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }
        

    }

}
