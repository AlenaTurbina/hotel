package com.hotel.dto;

import com.hotel.model.entity.Optional;
import com.hotel.model.entity.OrderStatus;
import com.hotel.model.entity.Room;
import com.hotel.model.entity.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderBookingDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateArrival;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDeparture;
    private Integer quantityPersons;
    private Integer roomType;
    private Integer classApartment;
    private List<Integer> optionals;
    private Integer user;


    private Integer id;
    private Integer room;
    private Integer orderStatus;
}
