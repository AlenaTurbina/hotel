package com.hotel.validator;

import com.hotel.dto.UserDTO;
import com.hotel.service.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@NoArgsConstructor
public class UserValidator implements Validator {

    private UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "document", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "validation.required");

        if (userDTO.getFirstName().length() < 2 || userDTO.getFirstName().length() > 32) {
            errors.rejectValue("firstName", "validation.size.registration.userName");
        }

        if (userDTO.getLastName().length() < 2 || userDTO.getFirstName().length() > 32) {
            errors.rejectValue("lastName", "validation.size.registration.userName");
        }

        if (userService.getByEmail(userDTO.getEmail()) != null) {
            errors.rejectValue("email", "validation.duplicate.registration.email");
        }

        if (!userDTO.getPhoneNumber().matches("^\\+[0-9]{9,20}$")) {
            errors.rejectValue("phoneNumber", "validation.pattern.registration.phoneNumber");
        }

        if (!userDTO.getEmail().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            errors.rejectValue("email", "validation.pattern.registration.email");
        }

        if (userDTO.getPassword().length() < 3 || userDTO.getPassword().length() > 32) {
            errors.rejectValue("password", "validation.size.registration.password");
        }

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            errors.rejectValue("password", "validation.different.registration.password");
        }
    }
}
