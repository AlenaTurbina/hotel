package com.hotel.validator;

import com.hotel.configuration.TestConfigurationValidator;
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
class ClassApartmentUpdateValidatorTest {

    @Autowired
    private ClassApartmentUpdateValidator classApartmentUpdateValidator;

    @Autowired
    private ClassApartmentService classApartmentService;

    private static final ClassApartment classApartment = mock(ClassApartment.class);
    private static final ClassApartment classApartmentExist = mock(ClassApartment.class);
    private static final Double testPriceValid = 10.0;
    private static final Double testPriceInvalid = -5.0;
    private static final Double testPriceInvalidZero = 0.0;

    @Test
    void testValidateShouldAcceptClassApartmentPositivePlacePrice() {
        when(classApartment.getPlacePrice()).thenReturn(testPriceValid);
        Errors errors = mock(Errors.class);
        classApartmentUpdateValidator.validate(classApartment, errors);

        verify(errors, never())
                .rejectValue("placePrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectClassApartmentNegativePlacePrice() {
        when(classApartment.getPlacePrice()).thenReturn(testPriceInvalid);
        Errors errors = mock(Errors.class);
        classApartmentUpdateValidator.validate(classApartment, errors);

        verify(errors, times(1))
                .rejectValue("placePrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectClassApartmentZeroPlacePrice() {
        when(classApartment.getPlacePrice()).thenReturn(testPriceInvalidZero);
        Errors errors = mock(Errors.class);
        classApartmentUpdateValidator.validate(classApartment, errors);

        verify(errors, times(1))
                .rejectValue("placePrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptClassApartmentNewName() {
        when(classApartmentService.getByName(any())).thenReturn(null);
        when(classApartment.getId()).thenReturn(1);
        Errors errors = mock(Errors.class);
        classApartmentUpdateValidator.validate(classApartment, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectClassApartmentSameName() {
        when(classApartmentService.getByName(any())).thenReturn(classApartment);
        when(classApartment.getId()).thenReturn(1);
        Errors errors = mock(Errors.class);
        classApartmentUpdateValidator.validate(classApartment, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectClassApartmentSameExistName() {
        when(classApartmentService.getByName(any())).thenReturn(classApartmentExist);
        when(classApartment.getId()).thenReturn(1);
        when(classApartmentExist.getId()).thenReturn(2);
        Errors errors = mock(Errors.class);
        classApartmentUpdateValidator.validate(classApartment, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

}