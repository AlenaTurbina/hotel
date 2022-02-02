package com.hotel.validator;

import com.hotel.configuration.TestConfigurationValidator;
import com.hotel.dto.RoomKindDTO;
import com.hotel.service.RoomKindService;
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
class RoomKindValidatorTest {

    @Autowired
    private RoomKindValidator roomKindValidator;

    @Autowired
    private RoomKindService roomKindService;

    private static final RoomKindDTO roomKindDTO = mock(RoomKindDTO.class);
    private static final Double testPriceValid = 10.0;
    private static final Double testPriceInvalid = -5.0;
    private static final Double testPriceInvalidZero = 0.0;

    @Test
    void testValidateShouldAcceptRoomKindDTOPositivePlacePrice() {
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceValid);
        Errors errors = mock(Errors.class);
        roomKindValidator.validate(roomKindDTO, errors);

        verify(errors, never())
                .rejectValue("roomPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectRoomKindDTONegativePlacePrice() {
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceInvalid);
        Errors errors = mock(Errors.class);
        roomKindValidator.validate(roomKindDTO, errors);

        verify(errors, times(1))
                .rejectValue("roomPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectRoomKindDTOZeroPlacePrice() {
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceInvalidZero);
        Errors errors = mock(Errors.class);
        roomKindValidator.validate(roomKindDTO, errors);

        verify(errors, times(1))
                .rejectValue("roomPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptRoomKindNewName() {
        when(roomKindService.getRoomKindByRoomTypeAndClassApartmentID(any(), any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        roomKindValidator.validate(roomKindDTO, errors);

        verify(errors, never())
                .rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
    }

    @Test
    void testValidateShouldRejectRoomKindExistName() {
        when(roomKindService.getRoomKindByRoomTypeAndClassApartmentID(any(), any())).thenReturn(1);
        when(roomKindDTO.getClassApartment()).thenReturn(1);
        when(roomKindDTO.getRoomType()).thenReturn(1);
        Errors errors = mock(Errors.class);
        roomKindValidator.validate(roomKindDTO, errors);

        verify(errors, times(1))
                .rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
    }

}