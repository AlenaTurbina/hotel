package com.hotel.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserDTO {
    @NotBlank( message = "{Required}")
    @Size(min=8, max=32, message = "{Size.registration.email}")
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "{Email.registration.pattern}")
    private String email;
    @NotBlank( message = "{Required}")
    @Size(min=3, max=32, message = "{Size.registration.password}")
    private String password;
    @NotBlank( message = "{Required}")
    @Size(min=2, max=32, message = "{Size.registration.userName}")
    private String firstName;
    @NotBlank( message = "{Required}")
    @Size(min=2, max=32, message = "{Size.registration.userName}")
    private String lastName;
    @NotBlank( message = "{Required}")
    transient private String confirmPassword;

//    private String phoneNumber;
//    private String document;
}
