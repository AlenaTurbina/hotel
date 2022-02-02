package com.hotel.dto;

import lombok.Data;


import javax.validation.constraints.Min;


@Data
public class RoomTypeDTO {

    private String name;
    @Min(value = 1, message = "The field must be positive")
    private Integer quantityPlaces;

}
