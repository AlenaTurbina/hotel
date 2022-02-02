package com.hotel.validator;

import com.hotel.configuration.TestConfigurationValidator;
import com.hotel.model.entity.RoomType;
import com.hotel.service.RoomTypeService;
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
class RoomTypeUpdateValidatorTest {

    @Autowired
    private RoomTypeUpdateValidator roomTypeUpdateValidator;

    @Autowired
    private RoomTypeService roomTypeService;

    private static final RoomType roomType = mock(RoomType.class);
    private static final RoomType roomTypeExist = mock(RoomType.class);


    @Test
    void testValidateShouldAcceptRoomTypeNewName() {
        when(roomTypeService.getByName(any())).thenReturn(null);
        when(roomType.getId()).thenReturn(1);
        Errors errors = mock(Errors.class);
        roomTypeUpdateValidator.validate(roomType, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectRoomTypeSameName() {
        when(roomTypeService.getByName(any())).thenReturn(roomType);
        when(roomType.getId()).thenReturn(1);
        Errors errors = mock(Errors.class);
        roomTypeUpdateValidator.validate(roomType, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectRoomTypeSameExistName() {
        when(roomTypeService.getByName(any())).thenReturn(roomTypeExist);
        when(roomType.getId()).thenReturn(1);
        when(roomTypeExist.getId()).thenReturn(2);
        Errors errors = mock(Errors.class);
        roomTypeUpdateValidator.validate(roomType, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

}