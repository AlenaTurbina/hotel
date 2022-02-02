package com.hotel.validator;

import com.hotel.model.entity.Optional;
import com.hotel.service.OptionalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class OptionalUpdateValidator implements Validator {
    OptionalService optionalService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Optional optional = (Optional) target;

        Integer checkID = checkOptional(optional);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "optionalPrice", "validation.required");

        if (optional.getOptionalPrice() <= 0) {
            errors.rejectValue("optionalPrice", "validation.field.positive");
        }

        if (checkID != null && checkID != optional.getId()) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }

    }

    public Integer checkOptional(Optional optional) {
        Optional findOptional = optionalService.getByName(optional.getName());
        if(findOptional !=null){
            return findOptional.getId();
        } return null;
    }

}
