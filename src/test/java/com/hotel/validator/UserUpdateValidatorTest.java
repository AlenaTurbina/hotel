package com.hotel.validator;

import com.hotel.configuration.TestConfigurationValidator;
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
class UserUpdateValidatorTest {

    @Autowired
    private UserClientUpdateValidator userClientUpdateValidator;

    @Autowired
    private UserService userService;

    private static final User user = mock(User.class);
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
    private static final String passwordInvalid = "1A";

    @Test
    void testValidateShouldRejectUserInvalidFirstName() {
        when(user.getFirstName()).thenReturn(firstNameInvalid);

        when(user.getLastName()).thenReturn(lastNameValid);
        when(user.getEmail()).thenReturn(emailValid);
        when(user.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(user.getPassword()).thenReturn(password);
        when(userService.getByEmail(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        userClientUpdateValidator.validate(user, errors);

        verify(errors, times(1))
                .rejectValue("firstName", "validation.size.registration.userName");
    }

    @Test
    void testValidateShouldRejectUserInvalidLastName() {
        when(user.getLastName()).thenReturn(lastNameInvalid);

        when(user.getFirstName()).thenReturn(firstNameValid);
        when(user.getEmail()).thenReturn(emailValid);
        when(user.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(user.getPassword()).thenReturn(password);
        when(userService.getByEmail(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        userClientUpdateValidator.validate(user, errors);

        verify(errors, times(1))
                .rejectValue("lastName", "validation.size.registration.userName");
    }

    @Test
    void testValidateShouldRejectUserInvalidEmail() {
        when(user.getEmail()).thenReturn(emailInvalid);

        when(user.getFirstName()).thenReturn(firstNameValid);
        when(user.getLastName()).thenReturn(lastNameValid);
        when(user.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(user.getPassword()).thenReturn(password);
        when(userService.getByEmail(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        userClientUpdateValidator.validate(user, errors);

        verify(errors, times(1))
                .rejectValue("email", "validation.pattern.registration.email");
    }

    @Test
    void testValidateShouldRejectUserInvalidPhoneNumber() {
        when(user.getPhoneNumber()).thenReturn(phoneNumberInvalid);

        when(user.getFirstName()).thenReturn(firstNameValid);
        when(user.getLastName()).thenReturn(lastNameValid);
        when(user.getEmail()).thenReturn(emailValid);
        when(user.getPassword()).thenReturn(password);
        when(userService.getByEmail(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        userClientUpdateValidator.validate(user, errors);

        verify(errors, times(1))
                .rejectValue("phoneNumber", "validation.pattern.registration.phoneNumber");
    }

    @Test
    void testValidateShouldRejectUserPasswordInvalid() {
        when(user.getPassword()).thenReturn(passwordInvalid);

        when(user.getFirstName()).thenReturn(firstNameValid);
        when(user.getLastName()).thenReturn(lastNameValid);
        when(user.getEmail()).thenReturn(emailValid);
        when(user.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userService.getByEmail(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        userClientUpdateValidator.validate(user, errors);

        verify(errors, times(1))
                .rejectValue("password", "validation.size.registration.password");
    }

    @Test
    void testValidateShouldAcceptUserEmailSame() {
        when(userService.getByEmail(any())).thenReturn(userExist);
        when(userExist.getId()).thenReturn(1);
        when(user.getId()).thenReturn(1);

        when(user.getFirstName()).thenReturn(firstNameValid);
        when(user.getLastName()).thenReturn(lastNameValid);
        when(user.getEmail()).thenReturn(emailValid);
        when(user.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(user.getPassword()).thenReturn(password);
        Errors errors = mock(Errors.class);
        userClientUpdateValidator.validate(user, errors);

        verify(errors, never())
                .rejectValue("email", "validation.duplicate.registration.email");
    }

    @Test
    void testValidateShouldAcceptUserEmailNew() {
        when(userService.getByEmail(any())).thenReturn(null);
        when(user.getId()).thenReturn(1);

        when(user.getFirstName()).thenReturn(firstNameValid);
        when(user.getLastName()).thenReturn(lastNameValid);
        when(user.getEmail()).thenReturn(emailValid);
        when(user.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(user.getPassword()).thenReturn(password);
        Errors errors = mock(Errors.class);
        userClientUpdateValidator.validate(user, errors);

        verify(errors, never())
                .rejectValue("email", "validation.duplicate.registration.email");
    }

    @Test
    void testValidateShouldRejectUserEmailExist() {
        when(userService.getByEmail(any())).thenReturn(userExist);
        when(userExist.getId()).thenReturn(2);
        when(user.getId()).thenReturn(1);

        when(user.getFirstName()).thenReturn(firstNameValid);
        when(user.getLastName()).thenReturn(lastNameValid);
        when(user.getEmail()).thenReturn(emailValid);
        when(user.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(user.getPassword()).thenReturn(password);
        Errors errors = mock(Errors.class);
        userClientUpdateValidator.validate(user, errors);

        verify(errors, times(1))
                .rejectValue("email", "validation.duplicate.registration.email");
    }

}