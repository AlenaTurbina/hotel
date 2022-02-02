package com.hotel.validator;

import com.hotel.configuration.TestConfigurationValidator;
import com.hotel.dto.RoomTypeDTO;
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
class RoomTypeValidatorTest {

    @Autowired
    private RoomTypeValidator roomTypeValidator;

    @Autowired
    private RoomTypeService roomTypeService;

    private static final RoomTypeDTO roomTypeDTO = mock(RoomTypeDTO.class);
    private static final RoomType roomTypeExist = mock(RoomType.class);


    @Test
    void testValidateShouldAcceptRoomTypeNewName() {
        when(roomTypeService.getByName(any())).thenReturn(null);
        Errors errors = mock(Errors.class);
        roomTypeValidator.validate(roomTypeDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectRoomTypeExistName() {
        when(roomTypeService.getByName(any())).thenReturn(roomTypeExist);
        Errors errors = mock(Errors.class);
        roomTypeValidator.validate(roomTypeDTO, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

}