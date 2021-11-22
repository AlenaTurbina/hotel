package com.hotel.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class OrderBookingDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateArrival;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDeparture;
    private Integer quantityPersons;
    private Integer roomType;
    private Integer classApartment;
}
