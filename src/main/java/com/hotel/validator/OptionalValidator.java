package com.hotel.validator;

import com.hotel.dto.OptionalDTO;
import com.hotel.dto.RoomTypeDTO;
import com.hotel.service.OptionalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class OptionalValidator implements Validator {
    OptionalService optionalService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        OptionalDTO optionalDTO = (OptionalDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "optionalPrice", "validation.required");

        if (optionalDTO.getOptionalPrice() <= 0) {
            errors.rejectValue("optionalPrice", "validation.field.positive");
        }

        if (optionalService.getByName(optionalDTO.getName()) !=null) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }
    }

}
