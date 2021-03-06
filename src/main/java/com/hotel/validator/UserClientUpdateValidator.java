package com.hotel.validator;

import com.hotel.dto.UserDTO;
import com.hotel.model.entity.User;
import com.hotel.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class UserClientUpdateValidator implements Validator {

    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "document", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "validation.required");


        if (user.getFirstName().length() < 2 || user.getFirstName().length() > 32) {
            errors.rejectValue("firstName", "validation.size.registration.userName");
        }

        if (user.getLastName().length() < 2 || user.getLastName().length() > 32) {
            errors.rejectValue("lastName", "validation.size.registration.userName");
        }

        if (user.getPassword().length() < 3 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "validation.size.registration.password");
        }


        if (userService.getByEmail(user.getEmail()) != null && userService.getByEmail(user.getEmail()).getId() != user.getId()) {
            errors.rejectValue("email", "validation.duplicate.registration.email");
        }

        if (!user.getPhoneNumber().matches("^\\+[0-9]{9,20}$")) {
            errors.rejectValue("phoneNumber", "validation.pattern.registration.phoneNumber");
        }

        if (!user.getEmail().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            errors.rejectValue("email", "validation.pattern.registration.email");
        }

    }
}
