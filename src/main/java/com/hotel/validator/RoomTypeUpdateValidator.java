package com.hotel.validator;

import com.hotel.model.entity.RoomType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class RoomTypeUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoomType roomType = (RoomType) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantityPlaces", "validation.required");

        if (roomType.getQuantityPlaces() <= 0) {
            errors.rejectValue("quantityPlaces", "validation.field.positive");
        }
    }

}
