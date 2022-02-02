package com.hotel.validator;

import com.hotel.configuration.TestConfigurationValidator;
import com.hotel.model.entity.Optional;
import com.hotel.service.OptionalService;
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
class OptionalUpdateValidatorTest {

    @Autowired
    private OptionalUpdateValidator optionalUpdateValidator;

    @Autowired
    private OptionalService optionalService;

    private static final Optional optional = mock(Optional.class);
    private static final Optional optionalExist = mock(Optional.class);
    private static final Double testPriceValid = 1.0;
    private static final Double testPriceInvalid = -1.0;
    private static final Double testPriceZero = 0.0;

    @Test
    void testValidateShouldAcceptOptionalPositivePrice() {
        when(optional.getOptionalPrice()).thenReturn(testPriceValid);
        Errors errors = mock(Errors.class);
        optionalUpdateValidator.validate(optional, errors);

        verify(errors, never())
                .rejectValue("optionalPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectOptionalNegativePrice() {
        when(optional.getOptionalPrice()).thenReturn(testPriceInvalid);
        Errors errors = mock(Errors.class);
        optionalUpdateValidator.validate(optional, errors);

        verify(errors, times(1))
                .rejectValue("optionalPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectOptionalZeroPrice() {
        when(optional.getOptionalPrice()).thenReturn(testPriceZero);
        Errors errors = mock(Errors.class);
        optionalUpdateValidator.validate(optional, errors);

        verify(errors, times(1))
                .rejectValue("optionalPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptOptionalNewName() {
        when(optionalService.getByName(any())).thenReturn(null);
        when(optional.getId()).thenReturn(1);
        Errors errors = mock(Errors.class);
        optionalUpdateValidator.validate(optional, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectOptionalSameName() {
        when(optionalService.getByName(any())).thenReturn(optional);
        when(optional.getId()).thenReturn(1);
        Errors errors = mock(Errors.class);
        optionalUpdateValidator.validate(optional, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectOptionalSameExistName() {
        when(optionalService.getByName(any())).thenReturn(optionalExist);
        when(optional.getId()).thenReturn(1);
        when(optionalExist.getId()).thenReturn(2);
        Errors errors = mock(Errors.class);
        optionalUpdateValidator.validate(optional, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

}