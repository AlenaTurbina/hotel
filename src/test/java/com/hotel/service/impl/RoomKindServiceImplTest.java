package com.hotel.service.impl;

import com.hotel.model.entity.ClassApartment;
import com.hotel.model.entity.RoomKind;
import com.hotel.model.entity.RoomType;
import com.hotel.model.repository.RoomKindRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class RoomKindServiceImplTest {
    @Mock
    private RoomKindRepository roomKindRepository;

    @InjectMocks
    private RoomKindServiceImpl roomKindService;

    private RoomKind roomKind;

    ClassApartment classApartment1 = new ClassApartment(1, null, null);
    ClassApartment classApartment2 = new ClassApartment(2, null, null);

    RoomType roomType1 = new RoomType(1, null, null);
    RoomType roomType2 = new RoomType(2, null, null);

    RoomKind roomKind1 = new RoomKind(1, null, roomType1, classApartment1);
    RoomKind roomKind2 = new RoomKind(2, null, roomType1, classApartment2);
    RoomKind roomKind3 = new RoomKind(3, null, roomType2, classApartment1);
    RoomKind roomKind4 = new RoomKind(4, null, roomType2, classApartment2);


    @Test
    void testGetAll() {
        List<RoomKind> roomKinds = new ArrayList<>(List.of(roomKind1, roomKind2, roomKind3));
        Mockito.when(roomKindRepository.findAll()).thenReturn(roomKinds);
        List<RoomKind> roomKindExpected = new ArrayList<>(List.of(roomKind1, roomKind2, roomKind3));
        List<RoomKind> roomKindActual = roomKindService.getAll();

        assertEquals(roomKindExpected, roomKindActual);
    }

    @Test
    void testGetById() {
        Integer id = 10;
        roomKind = new RoomKind(id, null, null, null);
        Mockito.when(roomKindRepository.getById(any())).thenReturn(roomKind);
        RoomKind roomKindTest = roomKindService.getById(id);

        assertEquals(id, roomKindTest.getId());
    }

    @Test
    void testGetListUniqueClassApartmentsFromRoomKinds() {
        List<RoomKind> roomKinds = new ArrayList<>(List.of(roomKind1, roomKind2, roomKind3, roomKind4));
        Mockito.when(roomKindRepository.findAll()).thenReturn(roomKinds);
        List<ClassApartment> classApartmentsExpected = new ArrayList<>(List.of(classApartment1, classApartment2));
        List<ClassApartment> classApartmentsActual = roomKindService.getListUniqueClassApartmentsFromRoomKinds();

        assertArrayEquals(classApartmentsExpected.toArray(), classApartmentsActual.toArray());
    }

    @Test
    void testGetListUniqueRoomTypesFromRoomKinds() {
        List<RoomKind> roomKinds = new ArrayList<>(List.of(roomKind1, roomKind2, roomKind3, roomKind4));
        Mockito.when(roomKindRepository.findAll()).thenReturn(roomKinds);
        List<RoomType> roomTypesExpected = new ArrayList<>(List.of(roomType1, roomType2));
        List<RoomType> roomTypesActual = roomKindService.getListUniqueRoomTypesFromRoomKinds();

        assertArrayEquals(roomTypesExpected.toArray(), roomTypesActual.toArray());
    }

    @Test
    void testGetRoomKindByRoomTypeAndClassApartmentIDResultIsNotNull() {
        Integer roomTypeID = 1;
        Integer classApartmentID = 1;
        List<RoomKind> roomKinds = new ArrayList<>(List.of(roomKind1, roomKind2, roomKind3, roomKind4));
        Mockito.when(roomKindRepository.findAll()).thenReturn(roomKinds);
        Integer roomKindIDExpected = 1;
        Integer roomKindIDActual = roomKindService.getRoomKindByRoomTypeAndClassApartmentID(roomTypeID, classApartmentID);

        assertEquals(roomKindIDExpected, roomKindIDActual);
    }

    @Test
    void testGetRoomKindByRoomTypeAndClassApartmentIDResultIsNull() {
        Integer roomTypeID = 1;
        Integer classApartmentID = 3;
        List<RoomKind> roomKinds = new ArrayList<>(List.of(roomKind1, roomKind2, roomKind3, roomKind4));
        Mockito.when(roomKindRepository.findAll()).thenReturn(roomKinds);
        Integer roomKindIDActual = roomKindService.getRoomKindByRoomTypeAndClassApartmentID(roomTypeID, classApartmentID);

        assertNull(roomKindIDActual);
    }
}