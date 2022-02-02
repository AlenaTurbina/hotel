package com.hotel.validator;

import com.hotel.model.entity.ClassApartment;
import com.hotel.service.ClassApartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class ClassApartmentUpdateValidator implements Validator {
    ClassApartmentService classApartmentService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClassApartment classApartment = (ClassApartment) target;

        Integer checkID = checkClassApartment(classApartment);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "placePrice", "validation.required");

        if (classApartment.getPlacePrice() <= 0) {
            errors.rejectValue("placePrice", "validation.field.positive");
        }


        if (checkID != null && checkID != classApartment.getId()) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }
    }

    public Integer checkClassApartment(ClassApartment classApartment) {
        ClassApartment findClassApartment = classApartmentService.getByName(classApartment.getName());
        if(findClassApartment !=null){
            return findClassApartment.getId();
        } return null;
    }

}
