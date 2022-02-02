package com.hotel.validator;

import com.hotel.configuration.TestConfigurationValidator;
import com.hotel.dto.RoomDTO;
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
class RoomValidatorTest {

    @Autowired
    private RoomValidator roomValidator;

    @Autowired
    private RoomService roomService;

    private static final RoomDTO roomDTO = mock(RoomDTO.class);
    private static final Room roomExist = mock(Room.class);

    @Test
    void testValidateShouldAcceptClassApartmentNewName() {
        when(roomService.getByName(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        roomValidator.validate(roomDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectClassApartmentExistName() {
        when(roomService.getByName(any())).thenReturn(roomExist);
        Errors errors = mock(Errors.class);
        roomValidator.validate(roomDTO, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

}