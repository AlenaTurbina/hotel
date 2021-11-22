package com.hotel.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private String phoneNumber;
    private String document;
}
