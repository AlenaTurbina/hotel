package com.hotel.service.impl;

import com.hotel.dto.OrderBookingDTO;
import com.hotel.model.entity.*;
import com.hotel.model.repository.OrderBookingRepository;
import com.hotel.service.OptionalService;
import com.hotel.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hotel.utilit.Constant.ID_DEFAULT_ORDER_STATUS_CANCEL;
import static com.hotel.utilit.Constant.ID_DEFAULT_ORDER_STATUS_PAID;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

//to run the test with the SpringExtension class
//integrates the Spring TestContext Framework
@ExtendWith(SpringExtension.class)
class OrderBookingServiceImplTest {
    @Mock       //creates a mock
    private OrderBookingRepository orderBookingRepository;
    @Mock
    private RoomService roomService;
    @Mock
    private OptionalService optionalService;

    @InjectMocks //creates an instance of the class and injects the mocks
    private OrderBookingServiceImpl orderBookingService;

    OrderStatus orderStatusPaid = new OrderStatus(ID_DEFAULT_ORDER_STATUS_PAID, "Paid");
    OrderStatus orderStatusCancel = new OrderStatus(ID_DEFAULT_ORDER_STATUS_CANCEL, "Cancel");

    ClassApartment classApartment1 = new ClassApartment(1, null, null);
    ClassApartment classApartment2 = new ClassApartment(2, null, null);

    RoomType roomType1 = new RoomType(1, null, null);
    RoomType roomType2 = new RoomType(2, null, null);

    RoomKind roomKind1 = new RoomKind(1, null, roomType1, classApartment1);
    RoomKind roomKind2 = new RoomKind(2, null, roomType1, classApartment2);
    RoomKind roomKind3 = new RoomKind(3, null, roomType2, classApartment1);

    Room room1 = new Room(1, null, roomKind1, null);
    Room room2 = new Room(2, null, roomKind1, null);
    Room room3 = new Room(3, null, roomKind1, null);
    Room room4 = new Room(4, null, roomKind2, null);
    Room room5 = new Room(5, null, roomKind3, null);
    Room room6 = new Room(6, null, roomKind3, null);

    OrderBookingDTO orderBookingDTO1 = new OrderBookingDTO();


    @Test
    void testGetListOrderBookingsWithToday() {
        //Check case dateArrival = today < dateDeparture
        OrderBooking orderBooking1 = new OrderBooking(null, null, LocalDate.now(), LocalDate.now().plusDays(1),
                null, null, null, null, orderStatusPaid, null);

        //Check case dateArrival < today = dateDeparture
        OrderBooking orderBooking2 = new OrderBooking(null, null, LocalDate.now().minusDays(1), LocalDate.now(),
                null, null, null, null, orderStatusPaid, null);

        //Check case dateArrival < today < dateDeparture
        OrderBooking orderBooking3 = new OrderBooking(null, null, LocalDate.now().minusDays(3), LocalDate.now().plusDays(3),
                null, null, null, null, orderStatusPaid, null);

        //Failed result - dates after today
        OrderBooking orderBooking4 = new OrderBooking(null, null, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
                null, null, null, null, orderStatusPaid, null);

        //Failed result - dates before today
        OrderBooking orderBooking5 = new OrderBooking(null, null, LocalDate.now().minusDays(5), LocalDate.now().minusDays(1),
                null, null, null, null, orderStatusPaid, null);

        //Failed result - orderStatus = CANCEL
        OrderBooking orderBooking6 = new OrderBooking(null, null, LocalDate.now().minusDays(5), LocalDate.now().plusDays(5),
                null, null, null, null, orderStatusCancel, null);

        List<OrderBooking> orderBookings = new ArrayList<>(List.of(orderBooking1, orderBooking2, orderBooking3, orderBooking4, orderBooking5, orderBooking6));
        Mockito.when(orderBookingRepository.findAll()).thenReturn(orderBookings);

        List<OrderBooking> orderBookingsExpected = new ArrayList<>(List.of(orderBooking1, orderBooking2, orderBooking3));

        List<OrderBooking> orderBookingsActual = orderBookingService.getListOrderBookingsWithToday();

        assertArrayEquals(orderBookingsExpected.toArray(), orderBookingsActual.toArray());
    }

    @Test
    void testCalculationSumTotalWithOptionalsNotNull() {
        ClassApartment classApartment = new ClassApartment(1, null, 20.0);
        RoomKind roomKind = new RoomKind(1, 100.0, null, classApartment);
        Room room = new Room(1, null, roomKind, null);
        Optional optional1 = new Optional(1, null, 5.0);
        Optional optional2 = new Optional(2, null, 10.0);
        List<Optional> optionals = new ArrayList<>(List.of(optional1, optional2));
        List<Integer> optionalsID = new ArrayList<>(List.of(optional1.getId(), optional2.getId()));
        Mockito.when(optionalService.getListById(any())).thenReturn(optionals);

        orderBookingDTO1.setDateArrival(LocalDate.of(2022, 01, 05));
        orderBookingDTO1.setDateDeparture(LocalDate.of(2022, 01, 10));
        orderBookingDTO1.setQuantityPersons(2);
        orderBookingDTO1.setUser(null);
        orderBookingDTO1.setRoomType(null);
        orderBookingDTO1.setClassApartment(null);
        orderBookingDTO1.setOptionals(optionalsID);

        int daysRent = 10 - 5;
        int person = 2;
        double roomPrice = 100;
        double placePrice = 20;
        double optionalPrice1 = 5;
        double optionalPrice2 = 10;
        Double sumRoom = daysRent * roomPrice;
        Double sumClassApartments = daysRent * person * placePrice;
        Double sumOptionals = daysRent * optionalPrice1 + daysRent * optionalPrice2;
        Double sumTotalExpected = sumRoom + sumClassApartments + sumOptionals;
        Double sumTotalActual = orderBookingService.calculationSumTotal(orderBookingDTO1, room);

        assertEquals(sumTotalExpected, sumTotalActual, 0.000001);
    }

    @Test
    void testCalculationSumTotalWithOptionalsNull() {
        ClassApartment classApartment = new ClassApartment(1, null, 20.0);
        RoomKind roomKind = new RoomKind(1, 100.0, null, classApartment);
        Room room = new Room(1, null, roomKind, null);

        orderBookingDTO1.setDateArrival(LocalDate.of(2022, 01, 01));
        orderBookingDTO1.setDateDeparture(LocalDate.of(2022, 01, 16));
        orderBookingDTO1.setQuantityPersons(2);
        orderBookingDTO1.setUser(null);
        orderBookingDTO1.setRoomType(null);
        orderBookingDTO1.setClassApartment(null);
        orderBookingDTO1.setOptionals(null);

        int daysRent = 16 - 1;
        int person = 2;
        double roomPrice = 100;
        double placePrice = 20;
        Double sumRoom = daysRent * roomPrice;
        Double sumClassApartments = daysRent * person * placePrice;
        Double sumTotalExpected = sumRoom + sumClassApartments;
        Double sumTotalActual = orderBookingService.calculationSumTotal(orderBookingDTO1, room);

        assertEquals(sumTotalExpected, sumTotalActual, 0.000001);

    }

    @Test
    void testGetBusyRoomsOnSelectedDates() {
        LocalDate selectedDateArrival = LocalDate.of(2022, 01, 05);
        LocalDate selectedDateDeparture = LocalDate.of(2022, 01, 10);

        OrderBooking orderBooking1 = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 10),
                null, null, room1, null, orderStatusPaid, null);
        OrderBooking orderBooking2 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 15),
                null, null, room2, null, orderStatusPaid, null);
        OrderBooking orderBooking3 = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 07),
                null, null, room3, null, orderStatusPaid, null);
        //Failed result - dates before selected
        OrderBooking orderBooking4 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 04),
                null, null, room4, null, orderStatusPaid, null);
        //Failed result - orderStatus = CANCEL
        OrderBooking orderBooking5 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 15),
                null, null, room5, null, orderStatusCancel, null);

        List<OrderBooking> orderBookings = new ArrayList<>(List.of(orderBooking1, orderBooking2, orderBooking3, orderBooking4, orderBooking5));
        Mockito.when(orderBookingRepository.findAll()).thenReturn(orderBookings);
        List<Room> roomsExpected = new ArrayList<>(List.of(room1, room2, room3));
        List<Room> roomsActual = orderBookingService.getBusyRoomsOnSelectedDates(selectedDateArrival, selectedDateDeparture);

        assertArrayEquals(roomsExpected.toArray(), roomsActual.toArray());
    }

    @Test
    void testGetListFreeRoomsOnOrderBookingDatesWithStatusNonCancel() {
        //testing order (this room will be added to the list for next choosing)
        OrderBooking orderBookingTest = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 10),
                null, null, room6, null, orderStatusPaid, null);

        //busy room because of dates
        OrderBooking orderBooking1 = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 10),
                null, null, room1, null, orderStatusPaid, null);
        //busy room because of dates
        OrderBooking orderBooking2 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 15),
                null, null, room2, null, orderStatusPaid, null);
        //busy room because of dates
        OrderBooking orderBooking3 = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 07),
                null, null, room3, null, orderStatusPaid, null);
        //free room - dates before dates of orderBookingTest
        OrderBooking orderBooking4 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 04),
                null, null, room4, null, orderStatusPaid, null);
        //free room - order status cancel
        OrderBooking orderBooking5 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 15),
                null, null, room5, null, orderStatusCancel, null);

        List<OrderBooking> orderBookings = new ArrayList<>(List.of(orderBookingTest, orderBooking1, orderBooking2, orderBooking3, orderBooking4, orderBooking5));
        Mockito.when(orderBookingRepository.findAll()).thenReturn(orderBookings);
        List<Room> rooms = new ArrayList<>(List.of(room1, room2, room3, room4, room5, room6));
        Mockito.when(roomService.getAll()).thenReturn(rooms);
        List<Room> roomsExpected = new ArrayList<>(List.of(room4, room5, room6));
        List<Room> roomsActual = orderBookingService.getListFreeRoomsOnOrderBookingDates(orderBookingTest);

        assertArrayEquals(roomsExpected.toArray(), roomsActual.toArray());
    }

    @Test
    void testGetListFreeRoomsOnOrderBookingDatesWithStatusCancel() {
        //testing order (this room will not be added to the list for next choosing because order status is cancel)
        OrderBooking orderBookingTest = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 10),
                null, null, room5, null, orderStatusCancel, null);
        //busy room because of dates
        OrderBooking orderBooking1 = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 10),
                null, null, room1, null, orderStatusPaid, null);
        //busy room because of dates
        OrderBooking orderBooking2 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 15),
                null, null, room2, null, orderStatusPaid, null);
        //busy room because of dates
        OrderBooking orderBooking3 = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 07),
                null, null, room3, null, orderStatusPaid, null);
        //free room - dates before dates of orderBookingTest
        OrderBooking orderBooking4 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 04),
                null, null, room4, null, orderStatusPaid, null);
        //busy room & the same room has orderBookingTest
        OrderBooking orderBooking5 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 15),
                null, null, room5, null, orderStatusPaid, null);

        List<OrderBooking> orderBookings = new ArrayList<>(List.of(orderBookingTest, orderBooking1, orderBooking2, orderBooking3, orderBooking4, orderBooking5));
        Mockito.when(orderBookingRepository.findAll()).thenReturn(orderBookings);
        List<Room> rooms = new ArrayList<>(List.of(room1, room2, room3, room4, room5, room6));
        Mockito.when(roomService.getAll()).thenReturn(rooms);
        List<Room> roomsExpected = new ArrayList<>(List.of(room4, room6));
        List<Room> roomsActual = orderBookingService.getListFreeRoomsOnOrderBookingDates(orderBookingTest);

        assertArrayEquals(roomsExpected.toArray(), roomsActual.toArray());
    }

    @Test
    void testGetFreeRooms() {
        List<Room> rooms = new ArrayList<>(List.of(room1, room2, room3, room4, room5, room6));
        Mockito.when(roomService.getAll()).thenReturn(rooms);

        orderBookingDTO1.setDateArrival(LocalDate.of(2022, 01, 05));
        orderBookingDTO1.setDateDeparture(LocalDate.of(2022, 01, 10));
        orderBookingDTO1.setQuantityPersons(null);
        orderBookingDTO1.setUser(null);
        orderBookingDTO1.setRoomType(null);
        orderBookingDTO1.setClassApartment(null);
        orderBookingDTO1.setOptionals(null);

        OrderBooking orderBooking1 = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 10),
                null, null, room1, null, orderStatusPaid, null);
        OrderBooking orderBooking2 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 15),
                null, null, room2, null, orderStatusPaid, null);
        OrderBooking orderBooking3 = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 07),
                null, null, room3, null, orderStatusPaid, null);

        List<OrderBooking> orderBookings = new ArrayList<>(List.of(orderBooking1, orderBooking2, orderBooking3));
        Mockito.when(orderBookingRepository.findAll()).thenReturn(orderBookings);
        Map<RoomKind, Long> roomsExpected = new HashMap<>();
        roomsExpected.put(roomKind1, 0L);
        roomsExpected.put(roomKind2, 1L);
        roomsExpected.put(roomKind3, 2L);
        Map<RoomKind, Long> roomsActual = orderBookingService.getFreeRooms(orderBookingDTO1);

        assertEqualsMap(roomsExpected, roomsActual);
    }

    @Test
    void testGetFirstRelevantFreeRoomWhenFreeRoomNotNull() {
        List<Room> rooms = new ArrayList<>(List.of(room1, room2, room3, room4, room5, room6));
        Mockito.when(roomService.getAll()).thenReturn(rooms);

        orderBookingDTO1.setDateArrival(LocalDate.of(2022, 01, 05));
        orderBookingDTO1.setDateDeparture(LocalDate.of(2022, 01, 10));
        orderBookingDTO1.setQuantityPersons(null);
        orderBookingDTO1.setUser(null);
        orderBookingDTO1.setRoomType(1);        //id of roomType1
        orderBookingDTO1.setClassApartment(1);  //id of classApartment1
        orderBookingDTO1.setOptionals(null);

        //the same with DTO room-kind
        OrderBooking orderBooking1 = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 10),
                null, null, room1, null, orderStatusPaid, null);
        OrderBooking orderBooking2 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 4),
                null, null, room2, null, orderStatusPaid, null);
        OrderBooking orderBooking3 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 15),
                null, null, room4, null, orderStatusPaid, null);
        OrderBooking orderBooking4 = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 07),
                null, null, room6, null, orderStatusPaid, null);

        List<OrderBooking> orderBookings = new ArrayList<>(List.of(orderBooking1, orderBooking2, orderBooking3));
        Mockito.when(orderBookingRepository.findAll()).thenReturn(orderBookings);
        Room expectedRoom = room2;
        Room actualRoom = orderBookingService.getFirstRelevantFreeRoom(orderBookingDTO1);

        assertEquals(expectedRoom, actualRoom);
    }

    @Test
    void testGetFirstRelevantFreeRoomWhenFreeRoomNull() {
        List<Room> rooms = new ArrayList<>(List.of(room1, room2, room3, room4, room5, room6));
        Mockito.when(roomService.getAll()).thenReturn(rooms);

        orderBookingDTO1.setDateArrival(LocalDate.of(2022, 01, 05));
        orderBookingDTO1.setDateDeparture(LocalDate.of(2022, 01, 10));
        orderBookingDTO1.setQuantityPersons(null);
        orderBookingDTO1.setUser(null);
        orderBookingDTO1.setRoomType(1);        //id of roomType1
        orderBookingDTO1.setClassApartment(2);  //id of classApartment2
        orderBookingDTO1.setOptionals(null);

        OrderBooking orderBooking1 = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 10),
                null, null, room1, null, orderStatusPaid, null);
        OrderBooking orderBooking2 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 4),
                null, null, room2, null, orderStatusPaid, null);
        //the same with DTO room-kind, it's only one in test and busy
        OrderBooking orderBooking3 = new OrderBooking(null, null, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 15),
                null, null, room4, null, orderStatusPaid, null);
        OrderBooking orderBooking4 = new OrderBooking(null, null, LocalDate.of(2022, 01, 05), LocalDate.of(2022, 01, 07),
                null, null, room6, null, orderStatusPaid, null);

        List<OrderBooking> orderBookings = new ArrayList<>(List.of(orderBooking1, orderBooking2, orderBooking3));
        Mockito.when(orderBookingRepository.findAll()).thenReturn(orderBookings);
        Room actualRoom = orderBookingService.getFirstRelevantFreeRoom(orderBookingDTO1);

        Assert.isNull(actualRoom, "null");
    }

    //method for comparison Map<RoomKind,Long>
    public static void assertEqualsMap(Map<RoomKind, Long> expected, Map<RoomKind, Long> actual) {
        assertEquals(expected.size(), actual.size());
        for (Map.Entry<RoomKind, Long> mapValue : expected.entrySet()) {
            RoomKind expectedKey = mapValue.getKey();   //RoomKind key
            Long actualValue = actual.get(expectedKey); //Long value
            assertNotNull(actualValue);
            assertEquals(mapValue.getValue(), actualValue);
        }
    }
}