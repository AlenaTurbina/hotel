package com.hotel.validator;

import com.hotel.configuration.TestConfigurationValidator;
import com.hotel.dto.UserDTO;
import com.hotel.model.entity.User;
import com.hotel.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.validation.Errors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfigurationValidator.class,
        loader = AnnotationConfigContextLoader.class)
class UserValidatorTest {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    private static final UserDTO userDTO = mock(UserDTO.class);
    private static final User userExist = mock(User.class);
    private static final String firstNameValid = "Ivan";
    private static final String firstNameInvalid = "I";
    private static final String lastNameValid = "Ivanov";
    private static final String lastNameInvalid = "I";
    private static final String emailValid = "test@test.com";
    private static final String emailInvalid = "t.@test.com";
    private static final String phoneNumberValid = "+1255555555555";
    private static final String phoneNumberInvalid = "+125";
    private static final String password = "123";
    private static final String passwordDifferent = "1234";
    private static final String passwordInvalid = "1A";


    @Test
    void testValidateShouldRejectUserDTOInvalidFirstName() {
        when(userDTO.getFirstName()).thenReturn(firstNameInvalid);

        when(userDTO.getLastName()).thenReturn(lastNameValid);
        when(userDTO.getEmail()).thenReturn(emailValid);
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userDTO.getPassword()).thenReturn(password);
        when(userDTO.getConfirmPassword()).thenReturn(password);
        when(userService.getByEmail(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        userValidator.validate(userDTO, errors);

        verify(errors, times(1))
                .rejectValue("firstName", "validation.size.registration.userName");
    }

    @Test
    void testValidateShouldRejectUserDTOInvalidLastName() {
        when(userDTO.getLastName()).thenReturn(lastNameInvalid);

        when(userDTO.getFirstName()).thenReturn(firstNameValid);
        when(userDTO.getEmail()).thenReturn(emailValid);
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userDTO.getPassword()).thenReturn(password);
        when(userDTO.getConfirmPassword()).thenReturn(password);
        when(userService.getByEmail(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        userValidator.validate(userDTO, errors);

        verify(errors, times(1))
                .rejectValue("lastName", "validation.size.registration.userName");
    }

    @Test
    void testValidateShouldRejectUserDTOInvalidEmail() {
        when(userDTO.getEmail()).thenReturn(emailInvalid);

        when(userDTO.getFirstName()).thenReturn(firstNameValid);
        when(userDTO.getLastName()).thenReturn(lastNameValid);
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userDTO.getPassword()).thenReturn(password);
        when(userDTO.getConfirmPassword()).thenReturn(password);
        when(userService.getByEmail(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        userValidator.validate(userDTO, errors);

        verify(errors, times(1))
                .rejectValue("email", "validation.pattern.registration.email");
    }

    @Test
    void testValidateShouldRejectUserDTOInvalidPhoneNumber() {
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberInvalid);

        when(userDTO.getFirstName()).thenReturn(firstNameValid);
        when(userDTO.getLastName()).thenReturn(lastNameValid);
        when(userDTO.getEmail()).thenReturn(emailValid);
        when(userDTO.getPassword()).thenReturn(password);
        when(userDTO.getConfirmPassword()).thenReturn(password);
        when(userService.getByEmail(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        userValidator.validate(userDTO, errors);

        verify(errors, times(1))
                .rejectValue("phoneNumber", "validation.pattern.registration.phoneNumber");
    }

    @Test
    void testValidateShouldRejectUserDTOPasswordsDifferent() {
        when(userDTO.getPassword()).thenReturn(password);
        when(userDTO.getConfirmPassword()).thenReturn(passwordDifferent);

        when(userDTO.getFirstName()).thenReturn(firstNameValid);
        when(userDTO.getLastName()).thenReturn(lastNameValid);
        when(userDTO.getEmail()).thenReturn(emailValid);
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userService.getByEmail(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        userValidator.validate(userDTO, errors);

        verify(errors, times(1))
                .rejectValue("password", "validation.different.registration.password");
    }

    @Test
    void testValidateShouldRejectUserDTOPasswordInvalid() {
        when(userDTO.getPassword()).thenReturn(passwordInvalid);
        when(userDTO.getConfirmPassword()).thenReturn(passwordInvalid);

        when(userDTO.getFirstName()).thenReturn(firstNameValid);
        when(userDTO.getLastName()).thenReturn(lastNameValid);
        when(userDTO.getEmail()).thenReturn(emailValid);
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userService.getByEmail(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        userValidator.validate(userDTO, errors);

        verify(errors, times(1))
                .rejectValue("password", "validation.size.registration.password");
    }

    @Test
    void testValidateShouldRejectUserDTOEmailExist() {
        when(userService.getByEmail(any())).thenReturn(userExist);

        when(userDTO.getFirstName()).thenReturn(firstNameValid);
        when(userDTO.getLastName()).thenReturn(lastNameValid);
        when(userDTO.getEmail()).thenReturn(emailValid);
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userDTO.getPassword()).thenReturn(password);
        when(userDTO.getConfirmPassword()).thenReturn(password);
        Errors errors = mock(Errors.class);
        userValidator.validate(userDTO, errors);

        verify(errors, times(1))
                .rejectValue("email", "validation.duplicate.registration.email");
    }

}