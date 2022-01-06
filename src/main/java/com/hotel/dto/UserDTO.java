package com.hotel.dto;

import lombok.Data;

import javax.persistence.Transient;


@Data
public class UserDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String document;

    @Transient
    private String confirmPassword;

}
