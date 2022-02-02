package com.hotel.service.impl;

import com.hotel.model.entity.ClassApartment;
import com.hotel.model.entity.Room;
import com.hotel.model.entity.RoomKind;
import com.hotel.model.entity.RoomType;
import com.hotel.model.repository.RoomRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class RoomServiceImplTest {
    @Mock
    RoomRepository roomRepository;

    @InjectMocks
    RoomServiceImpl roomService;

    ClassApartment classApartment1 = new ClassApartment(1, null, null);
    ClassApartment classApartment2 = new ClassApartment(2, null, null);

    RoomType roomType1 = new RoomType(1, null, null);

    RoomKind roomKind1 = new RoomKind(1, null, roomType1, classApartment1);
    RoomKind roomKind2 = new RoomKind(2, null, roomType1, classApartment2);

    Room room1 = new Room(1, "1A", roomKind1, null);
    Room room2 = new Room(2, "2A", roomKind1, null);
    Room room3 = new Room(3, "3A", roomKind2, null);
    Room room4 = new Room(4, "4A", roomKind2, null);

    @Test
    void testGtAll() {
        List<Room> rooms = new ArrayList<>(List.of(room1, room2, room3, room4));
        Mockito.when(roomRepository.findAll()).thenReturn(rooms);
        List<Room> roomExpected = new ArrayList<>(List.of(room1, room2, room3, room4));
        List<Room> roomActual = roomService.getAll();

        assertEquals(roomExpected, roomActual);
    }

    @Test
    void testGetById() {
        Integer id = 10;
        Room room = new Room(id, null, null, null);
        Mockito.when(roomRepository.getById(any())).thenReturn(room);
        Room roomTest = roomService.getById(id);

        assertEquals(id, roomTest.getId());
    }

    @Test
    void testGetByName() {
        Mockito.when(roomRepository.findRoomByName(any())).thenReturn(room1);

        Assert.assertEquals(room1, roomService.getByName("A1"));
    }

    @Test
    void testGetListUniqueClassApartmentsFromRooms() {
        List<Room> rooms = new ArrayList<>(List.of(room1, room2, room3, room4));
        Mockito.when(roomRepository.findAll()).thenReturn(rooms);
        List<ClassApartment> classApartmentsExpected = new ArrayList<>(List.of(classApartment1, classApartment2));
        List<ClassApartment> classApartmentsActual = roomService.getListUniqueClassApartmentsFromRooms();

        assertArrayEquals(classApartmentsExpected.toArray(), classApartmentsActual.toArray());
    }

    @Test
    void testGetListUniqueRoomTypesFromRooms() {
        List<Room> rooms = new ArrayList<>(List.of(room1, room2, room3, room4));
        Mockito.when(roomRepository.findAll()).thenReturn(rooms);
        List<RoomType> roomTypesExpected = new ArrayList<>(List.of(roomType1));
        List<RoomType> roomTypesActual = roomService.getListUniqueRoomTypesFromRooms();

        assertArrayEquals(roomTypesExpected.toArray(), roomTypesActual.toArray());
    }

    @Test
    void testGetListUniqueRoomKindsFromRooms() {
        List<Room> rooms = new ArrayList<>(List.of(room1, room2, room3, room4));
        Mockito.when(roomRepository.findAll()).thenReturn(rooms);
        List<RoomKind> roomKindsExpected = new ArrayList<>(List.of(roomKind1, roomKind2));
        List<RoomKind> roomKindsActual = roomService.getListUniqueRoomKindsFromRooms();

        assertArrayEquals(roomKindsExpected.toArray(), roomKindsActual.toArray());
    }
}