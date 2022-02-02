package com.hotel.validator;

import com.hotel.configuration.TestConfigurationValidator;
import com.hotel.dto.OptionalDTO;
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
class OptionalValidatorTest {

    @Autowired
    private OptionalValidator optionalValidator;

    @Autowired
    private OptionalService optionalService;

    private static final OptionalDTO optionalDTO = mock(OptionalDTO.class);
    private static final Optional optionalExist = mock(Optional.class);
    private static final Double testPriceValid = 10.0;
    private static final Double testPriceInvalid = -5.0;
    private static final Double testPriceInvalidZero = 0.0;

    @Test
    void testValidateShouldAcceptOptionalDTOPositiveOptionalPrice() {
        when(optionalDTO.getOptionalPrice()).thenReturn(testPriceValid);
        Errors errors = mock(Errors.class);
        optionalValidator.validate(optionalDTO, errors);

        verify(errors, never())
                .rejectValue("optionalPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectOptionalDTONegativeOptionalPrice() {
        when(optionalDTO.getOptionalPrice()).thenReturn(testPriceInvalid);
        Errors errors = mock(Errors.class);
        optionalValidator.validate(optionalDTO, errors);

        verify(errors, times(1))
                .rejectValue("optionalPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectOptionalDTOZeroOptionalPrice() {
        when(optionalDTO.getOptionalPrice()).thenReturn(testPriceInvalidZero);
        Errors errors = mock(Errors.class);
        optionalValidator.validate(optionalDTO, errors);

        verify(errors, times(1))
                .rejectValue("optionalPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptOptionalNewName() {
        when(optionalService.getByName(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        optionalValidator.validate(optionalDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectOptionalExistName() {
        when(optionalService.getByName(any())).thenReturn(optionalExist);
        Errors errors = mock(Errors.class);
        optionalValidator.validate(optionalDTO, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

}