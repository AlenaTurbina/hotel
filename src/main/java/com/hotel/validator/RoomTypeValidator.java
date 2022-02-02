package com.hotel.validator;

import com.hotel.dto.RoomTypeDTO;
import com.hotel.service.RoomTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class RoomTypeValidator implements Validator {
    RoomTypeService roomTypeService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoomTypeDTO roomTypeDTO = (RoomTypeDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantityPlaces", "validation.required");

        if (roomTypeService.getByName(roomTypeDTO.getName()) !=null) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }
    }

}
