package com.hotel.validator;

import com.hotel.dto.OrderBookingDTO;
import com.hotel.service.RoomTypeService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;

import static com.hotel.utilit.Constant.MAX_DAYS_BOOKING_PERIOD;
import static com.hotel.utilit.Constant.MAX_DAYS_DATE_ARRIVAL;


@Component
@NoArgsConstructor
public class OrderBookingValidator implements Validator {

    private RoomTypeService roomTypeService;

    @Autowired
    public OrderBookingValidator(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderBookingDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    OrderBookingDTO orderBookingDTO = (OrderBookingDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateArrival", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateDeparture", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantityPersons", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomType", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "classApartment", "validation.required");

        if(LocalDate.now().isAfter(orderBookingDTO.getDateArrival())){
            errors.rejectValue("dateArrival", "validation.booking.dateArrival.min");
        }

        if(orderBookingDTO.getDateDeparture().isBefore(orderBookingDTO.getDateArrival())){
            errors.rejectValue("dateDeparture", "validation.booking.dateDeparture.min");
        }

        if(orderBookingDTO.getDateArrival().isAfter(LocalDate.now().plusDays(MAX_DAYS_DATE_ARRIVAL))){
            errors.rejectValue("dateDeparture", "validation.booking.dateArrival.max");
        }

        if(orderBookingDTO.getDateDeparture().isAfter(orderBookingDTO.getDateArrival().plusDays(MAX_DAYS_BOOKING_PERIOD))){
            errors.rejectValue("dateDeparture", "validation.booking.dateDeparture.max");
        }

       if(orderBookingDTO.getQuantityPersons()>roomTypeService.getById(orderBookingDTO.getRoomType()).getQuantityPlaces()){
            errors.rejectValue("roomType", "validation.booking.quantityPersons.max");
       }

    }
}
