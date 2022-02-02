package com.hotel.validator;

import com.hotel.configuration.TestConfigurationValidator;
import com.hotel.model.entity.ClassApartment;
import com.hotel.model.entity.RoomKind;
import com.hotel.model.entity.RoomType;
import com.hotel.service.RoomKindService;
import org.junit.jupiter.api.BeforeAll;
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
class RoomKindUpdateValidatorTest {

    @Autowired
    private RoomKindUpdateValidator roomKindUpdateValidator;

    @Autowired
    private RoomKindService roomKindService;

    private static final RoomKind roomKind = mock(RoomKind.class);
    private static final RoomKind roomKindExist = mock(RoomKind.class);
    private static final ClassApartment classApartment = mock(ClassApartment.class);
    private static final RoomType roomType = mock(RoomType.class);
    private static final Double testPriceValid = 10.0;
    private static final Double testPriceInvalid = -5.0;
    private static final Double testPriceInvalidZero = 0.0;

    @BeforeAll
    public static void setUp() {
        when(roomKind.getClassApartment()).thenReturn(classApartment);
        when(classApartment.getId()).thenReturn(1);
        when(roomKind.getRoomType()).thenReturn(roomType);
        when(roomType.getId()).thenReturn(1);
        when(roomKind.getId()).thenReturn(1);
    }


    @Test
    void testValidateShouldAcceptRoomKindPositivePlacePrice() {
        when(roomKind.getRoomPrice()).thenReturn(testPriceValid);
        when(roomKindService.getRoomKindByRoomTypeAndClassApartmentID(any(), any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        roomKindUpdateValidator.validate(roomKind, errors);

        verify(errors, never())
                .rejectValue("roomPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectRoomKindNegativePlacePrice() {
        when(roomKind.getRoomPrice()).thenReturn(testPriceInvalid);
        when(roomKindService.getRoomKindByRoomTypeAndClassApartmentID(any(), any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        roomKindUpdateValidator.validate(roomKind, errors);

        verify(errors, times(1))
                .rejectValue("roomPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectRoomKindZeroPlacePrice() {
        when(roomKind.getRoomPrice()).thenReturn(testPriceInvalidZero);
        when(roomKindService.getRoomKindByRoomTypeAndClassApartmentID(any(), any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        roomKindUpdateValidator.validate(roomKind, errors);

        verify(errors, times(1))
                .rejectValue("roomPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptRoomKindNewName() {
        when(roomKind.getRoomPrice()).thenReturn(testPriceValid);
        when(roomKindService.getRoomKindByRoomTypeAndClassApartmentID(any(), any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        roomKindUpdateValidator.validate(roomKind, errors);

        verify(errors, never())
                .rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
    }

    @Test
    void testValidateShouldAcceptRoomKindSameName() {
        when(roomKind.getRoomPrice()).thenReturn(testPriceValid);
        when(roomKindService.getRoomKindByRoomTypeAndClassApartmentID(any(), any())).thenReturn(1);
        Errors errors = mock(Errors.class);
        roomKindUpdateValidator.validate(roomKind, errors);

        verify(errors, never())
                .rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
    }

    @Test
    void testValidateShouldRejectRoomKindExistName() {
        when(roomKind.getRoomPrice()).thenReturn(testPriceValid);
        when(roomKindExist.getId()).thenReturn(2);
        when(roomKindService.getRoomKindByRoomTypeAndClassApartmentID(any(), any())).thenReturn(2);
        Errors errors = mock(Errors.class);
        roomKindUpdateValidator.validate(roomKind, errors);

        verify(errors, times(1))
                .rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
    }

}