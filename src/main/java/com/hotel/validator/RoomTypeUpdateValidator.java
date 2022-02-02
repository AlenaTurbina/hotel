package com.hotel.validator;

import com.hotel.model.entity.RoomType;
import com.hotel.service.RoomTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class RoomTypeUpdateValidator implements Validator {
    RoomTypeService roomTypeService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoomType roomType = (RoomType) target;

        Integer checkID = checkRoomType(roomType);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantityPlaces", "validation.required");


        if (checkID != null && checkID != roomType.getId()) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }
    }

    public Integer checkRoomType(RoomType roomType) {
        RoomType findRoomType = roomTypeService.getByName(roomType.getName());
        if(findRoomType !=null){
            return findRoomType.getId();
        } return null;
    }

}
