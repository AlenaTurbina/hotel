package com.hotel.validator;

import com.hotel.model.entity.OrderBooking;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@NoArgsConstructor
public class OrderUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return OrderBooking.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderBooking orderBooking = (OrderBooking) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "room.id", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "orderStatus.id", "validation.required");

    }
}
