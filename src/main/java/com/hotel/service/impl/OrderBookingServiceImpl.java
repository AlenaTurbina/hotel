package com.hotel.service.impl;

import com.hotel.dto.OrderBookingDTO;
import com.hotel.model.entity.*;
import com.hotel.model.entity.Optional;
import com.hotel.model.repository.OrderBookingRepository;
import com.hotel.service.*;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static com.hotel.utilit.Constant.ID_DEFAULT_ORDER_STATUS_CANCEL;
import static com.hotel.utilit.Constant.ID_DEFAULT_ORDER_STATUS_WAIT;

@Service
@AllArgsConstructor
public class OrderBookingServiceImpl implements OrderBookingService {
    private OrderBookingRepository orderBookingRepository;
    private RoomService roomService;
    private OptionalService optionalService;
    private UserService userService;
    private OrderStatusService orderStatusService;

    @Override
    public List<OrderBooking> getAll() {
        return orderBookingRepository.findAll();
    }

    @Override
    public OrderBooking getById(Integer id) {
        return orderBookingRepository.getById(id);
    }

    @Override
    public OrderBooking save(OrderBookingDTO orderBookingDTO) {
        var room = getFirstRelevantFreeRoom(orderBookingDTO);
        if (room != null) {
            var sum = calculationSumTotal(orderBookingDTO, room);
            var optionals = optionalService.getListById(orderBookingDTO.getOptionals());
            var client = userService.getById(orderBookingDTO.getUser());
            var orderBooking = OrderBooking.builder()
                    .date(LocalDate.now())
                    .dateArrival(orderBookingDTO.getDateArrival())
                    .dateDeparture(orderBookingDTO.getDateDeparture())
                    .quantityPersons(orderBookingDTO.getQuantityPersons())
                    .client(client)
                    .room(room)
                    .sumTotal(sum)
                    .orderStatus(orderStatusService.getById(ID_DEFAULT_ORDER_STATUS_WAIT))
                    .optionals(optionals)
                    .build();
            orderBookingRepository.saveAndFlush(orderBooking);
            return orderBooking;
        } else {
            return null;
        }
    }

    @Override
    public OrderBooking update(OrderBooking orderBooking) {
        var orderBookingUpdate = OrderBooking.builder()
                .id(orderBooking.getId())
                .date(orderBooking.getDate())
                .dateArrival(orderBooking.getDateArrival())
                .dateDeparture(orderBooking.getDateDeparture())
                .quantityPersons(orderBooking.getQuantityPersons())
                .client(userService.getById(orderBooking.getClient().getId()))
                .room(roomService.getById(orderBooking.getRoom().getId()))
                .sumTotal(orderBooking.getSumTotal())
                .orderStatus(orderStatusService.getById(orderBooking.getOrderStatus().getId()))
                .optionals(orderBooking.getOptionals())
                .build();
        orderBookingRepository.saveAndFlush(orderBookingUpdate);
        return orderBookingUpdate;
    }

    @Override
    public Double calculationSumTotal(OrderBookingDTO orderBookingDTO, Room room) {
        double sumOptionals = 0;

        var daysRent = Period.between(orderBookingDTO.getDateArrival(), orderBookingDTO.getDateDeparture()).getDays();
        Double sumRoom = daysRent * room.getRoomKind().getRoomPrice();
        Double sumClassApartments = daysRent * room.getRoomKind().getClassApartment().getPlacePrice() * orderBookingDTO.getQuantityPersons();
        var optionals = optionalService.getListById(orderBookingDTO.getOptionals());
        if (!optionals.isEmpty()) {
            for (Optional optional : optionals) {
                sumOptionals += daysRent * optional.getOptionalPrice();
            }
        }

        Double sumTotal = sumRoom + sumClassApartments + sumOptionals;

        return sumTotal;
    }


    //Free room of a given type for the desired dates /for booking/
    @Override
    public Room getFirstRelevantFreeRoom(OrderBookingDTO orderBookingDTO) {
        //getting a list of busy rooms of relevant characteristics
        var busyRooms = getBusyRoomsOnSelectedDates(orderBookingDTO.getDateArrival(), orderBookingDTO.getDateDeparture())
                .stream()
                .filter(room -> room.getRoomKind().getRoomType().getId() == orderBookingDTO.getRoomType())
                .filter(room -> room.getRoomKind().getClassApartment().getId() == orderBookingDTO.getClassApartment())
                .collect(Collectors.toList());

        //getting a complete list of rooms of relevant characteristics
        var resultRooms = roomService.getAll()
                .stream()
                .filter(room -> room.getRoomKind().getRoomType().getId() == orderBookingDTO.getRoomType())
                .filter(room -> room.getRoomKind().getClassApartment().getId() == orderBookingDTO.getClassApartment())
                .collect(Collectors.toList());

        //getting a list of free rooms of relevant characteristics
        Iterator iterator = busyRooms.iterator();
        while (iterator.hasNext()) {
            resultRooms.remove(iterator.next());
        }

        if (resultRooms.size() != 0) {
            return resultRooms.get(0);
        } else return null;

    }


    //Quantity of free rooms of a given type for the desired dates
    @Override
    public Map<RoomKind, Long> getFreeRooms(OrderBookingDTO orderBookingDTO) {
        //getting a quantity of busy rooms, grouping by kind
        var busyRooms = getBusyRoomsOnSelectedDates(orderBookingDTO.getDateArrival(), orderBookingDTO.getDateDeparture())
                .stream()
                .collect(Collectors.groupingBy(Room::getRoomKind, Collectors.counting()));

        //getting a quantity of rooms, grouping by kind
        var resultRooms = roomService.getAll()
                .stream()
                .collect(Collectors.groupingBy(Room::getRoomKind, Collectors.counting()));

        //getting quantity of free rooms, grouping by kind
        for (Map.Entry<RoomKind, Long> entryRoom : resultRooms.entrySet()) {
            for (Map.Entry<RoomKind, Long> entryBusyRoom : busyRooms.entrySet()) {
                if (entryRoom.getKey().equals(entryBusyRoom.getKey()))
                    entryRoom.setValue(entryRoom.getValue() - entryBusyRoom.getValue());
            }
        }

        return resultRooms;
    }


    //List of free rooms of a given type for the desired dates for orderBooking Update
    @Override
    public List<Room> getListFreeRoomsOnOrderBookingDates(OrderBooking orderBooking) {
        //getting a  list of busy rooms taking into account the certain dates and order status (except CANCEL)
        var busyRooms = getBusyRoomsOnSelectedDates(orderBooking.getDateArrival(), orderBooking.getDateDeparture());

        //getting a complete list of rooms
        var resultRooms = roomService.getAll();

        //getting a list of free rooms on certain dates
        Iterator iterator = busyRooms.iterator();
        while (iterator.hasNext()) {
            resultRooms.remove(iterator.next());
        }

        //add room of current orderBooking for option
        if (!(orderBooking.getOrderStatus().getId() == ID_DEFAULT_ORDER_STATUS_CANCEL)) {
            resultRooms.add(orderBooking.getRoom());
        }

        return resultRooms;
    }

    //List of busy rooms on the selected dates
    @Override
    public List<Room> getBusyRoomsOnSelectedDates(LocalDate dateArrival, LocalDate dateDeparture) {
        //getting a complete list of orders
        var orderBookings = orderBookingRepository.findAll();
        //getting a list of busy rooms with taking into account the certain dates and order status (except CANCEL)
        var busyRooms = orderBookings.stream()
                .filter(order -> !(order.getOrderStatus().getId() == ID_DEFAULT_ORDER_STATUS_CANCEL))
                .filter(order -> !(order.getDateArrival().isAfter(dateDeparture) ||
                        order.getDateDeparture().isBefore(dateArrival) ||
                        order.getDateArrival().isEqual(dateDeparture) ||
                        order.getDateDeparture().isEqual(dateArrival)))
                .map(OrderBooking::getRoom)
                .distinct()
                .collect(Collectors.toList());

        return busyRooms;
    }


}







