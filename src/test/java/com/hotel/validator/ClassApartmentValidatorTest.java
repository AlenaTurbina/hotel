package com.hotel.validator;

import com.hotel.configuration.TestConfigurationValidator;
import com.hotel.dto.ClassApartmentDTO;
import com.hotel.model.entity.ClassApartment;
import com.hotel.service.ClassApartmentService;
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
class ClassApartmentValidatorTest {

    @Autowired
    private ClassApartmentValidator classApartmentValidator;

    @Autowired
    private ClassApartmentService classApartmentService;

    private static final ClassApartmentDTO classApartmentDTO = mock(ClassApartmentDTO.class);
    private static final ClassApartment classApartmentExist = mock(ClassApartment.class);
    private static final Double testPriceValid = 10.0;
    private static final Double testPriceInvalid = -5.0;
    private static final Double testPriceInvalidZero = 0.0;

    @Test
    void testValidateShouldAcceptClassApartmentDTOPositivePlacePrice() {
        when(classApartmentDTO.getPlacePrice()).thenReturn(testPriceValid);
        Errors errors = mock(Errors.class);
        classApartmentValidator.validate(classApartmentDTO, errors);

        verify(errors, never())
                .rejectValue("placePrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectClassApartmentDTONegativePlacePrice() {
        when(classApartmentDTO.getPlacePrice()).thenReturn(testPriceInvalid);
        Errors errors = mock(Errors.class);
        classApartmentValidator.validate(classApartmentDTO, errors);

        verify(errors, times(1))
                .rejectValue("placePrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectClassApartmentDTOZeroPlacePrice() {
        when(classApartmentDTO.getPlacePrice()).thenReturn(testPriceInvalidZero);
        Errors errors = mock(Errors.class);
        classApartmentValidator.validate(classApartmentDTO, errors);

        verify(errors, times(1))
                .rejectValue("placePrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptClassApartmentNewName() {
        when(classApartmentService.getByName(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        classApartmentValidator.validate(classApartmentDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectClassApartmentExistName() {
        when(classApartmentService.getByName(any())).thenReturn(classApartmentExist);
        Errors errors = mock(Errors.class);
        classApartmentValidator.validate(classApartmentDTO, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

}