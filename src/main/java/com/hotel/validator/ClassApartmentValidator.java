package com.hotel.validator;

import com.hotel.dto.ClassApartmentDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class ClassApartmentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClassApartmentDTO classApartmentDTO = (ClassApartmentDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "placePrice", "validation.required");

        if (classApartmentDTO.getPlacePrice() <= 0) {
            errors.rejectValue("placePrice", "validation.field.positive");
        }
    }

}
