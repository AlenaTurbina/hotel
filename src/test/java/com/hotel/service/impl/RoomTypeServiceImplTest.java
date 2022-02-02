package com.hotel.service.impl;

import com.hotel.model.entity.RoomType;
import com.hotel.model.repository.RoomTypeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class RoomTypeServiceImplTest {
    @Mock
    private RoomTypeRepository roomTypeRepository;

    @InjectMocks
    private RoomTypeServiceImpl roomTypeService;

    @Test
    void testGetAll() {
        Integer id1 = 1;
        Integer id2 = 2;
        Integer id3 = 3;
        RoomType roomType1 = new RoomType(id1, null, null);
        RoomType roomType2 = new RoomType(id2, null, null);
        RoomType roomType3 = new RoomType(id3, null, null);

        List<RoomType> roomTypes = new ArrayList<>(List.of(roomType1, roomType2, roomType3));
        Mockito.when(roomTypeRepository.findAll()).thenReturn(roomTypes);
        List<RoomType> roomTypesExpected = new ArrayList<>(List.of(roomType1, roomType2, roomType3));
        List<RoomType> roomTypesActual = roomTypeService.getAll();

        Assert.assertEquals(roomTypesExpected, roomTypesActual);
    }

    @Test
    void testGetById() {
        Integer id = 10;
        RoomType roomType = new RoomType(id, null, null);
        Mockito.when(roomTypeRepository.getById(any())).thenReturn(roomType);
        RoomType roomTypeTest = roomTypeService.getById(id);

        Assert.assertEquals(id, roomTypeTest.getId());
    }

    @Test
    void testGetByName() {
        RoomType roomType1 = new RoomType(1, "RT1", null);
        Mockito.when(roomTypeRepository.findRoomTypeByName(any())).thenReturn(roomType1);

        Assert.assertEquals(roomType1, roomTypeService.getByName("RT1"));
    }
}