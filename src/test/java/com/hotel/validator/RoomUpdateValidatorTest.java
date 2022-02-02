package com.hotel.validator;

import com.hotel.configuration.TestConfigurationValidator;
import com.hotel.model.entity.Room;
import com.hotel.service.RoomService;
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
class RoomUpdateValidatorTest {

    @Autowired
    private RoomUpdateValidator roomUpdateValidator;

    @Autowired
    private RoomService roomService;

    private static final Room room = mock(Room.class);
    private static final Room roomExist = mock(Room.class);


    @Test
    void testValidateShouldAcceptRoomNewName() {
        when(roomService.getByName(any())).thenReturn(null);
        when(room.getId()).thenReturn(1);
        Errors errors = mock(Errors.class);
        roomUpdateValidator.validate(room, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldAcceptRoomSameName() {
        when(roomService.getByName(any())).thenReturn(room);
        when(room.getId()).thenReturn(1);
        Errors errors = mock(Errors.class);
        roomUpdateValidator.validate(room, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectRoomSameExistName() {
        when(roomService.getByName(any())).thenReturn(roomExist);
        when(room.getId()).thenReturn(1);
        when(roomExist.getId()).thenReturn(2);
        Errors errors = mock(Errors.class);
        roomUpdateValidator.validate(room, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

}