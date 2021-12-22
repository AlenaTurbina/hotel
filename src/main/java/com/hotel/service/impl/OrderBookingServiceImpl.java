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

@Service
@AllArgsConstructor
public class OrderBookingServiceImpl implements OrderBookingService {
    private OrderBookingRepository orderBookingRepository;
    private RoomService roomService;
    private OptionalService optionalService;
    private UserService userService;
    private OrderStatusService orderStatusService;
    private final Integer userId = 1;
    private final Integer orderStatusId = 1;

    @Override
    public List<OrderBooking> getAll() {
        return orderBookingRepository.findAll();
    }

    @Override
    public OrderBooking getById(Integer id) {
        return orderBookingRepository.getById(id);
    }

    @Override
    public Double calculationSumTotal(OrderBookingDTO orderBookingDTO, Room room) {
        var daysRent = Period.between(orderBookingDTO.getDateArrival(), orderBookingDTO.getDateDeparture()).getDays();
        Double sumRoom = daysRent * room.getRoomKind().getRoomPrice();
        Double sumClassApartments = daysRent * room.getRoomKind().getClassApartment().getPlacePrice() * orderBookingDTO.getQuantityPersons();
        double sumOptionals = 0;
        var optionals = optionalService.getListById(orderBookingDTO.getOptionals());
        if (!optionals.isEmpty()) {
            for (Optional optional : optionals) {
                sumOptionals += daysRent * optional.getOptionalPrice();
            }
        }
        Double sumTotal = sumRoom + sumClassApartments + sumOptionals;
        return sumTotal;
    }

    @Override
    public OrderBooking save(OrderBookingDTO orderBookingDTO) {

        var room = getFirstFreeRoom(orderBookingDTO);
        if (room != null) {
            var summa = calculationSumTotal(orderBookingDTO, room);
            var optionals = optionalService.getListById(orderBookingDTO.getOptionals());
            var orderBooking = OrderBooking.builder()
                    .date(LocalDate.now())
                    .dateArrival(orderBookingDTO.getDateArrival())
                    .dateDeparture(orderBookingDTO.getDateDeparture())
                    .quantityPersons(orderBookingDTO.getQuantityPersons())
                    .client(userService.getById(userId))
                    .room(room)
                    .sumTotal(summa)
                    .orderStatus(orderStatusService.getById(orderStatusId))
                    .optionals(optionals)
                    .build();
            orderBookingRepository.saveAndFlush(orderBooking);
            return orderBooking;
        } else {
            return null;
        }
    }


    @Override
    public OrderBooking save1(OrderBookingDTO orderBookingDTO) {

        var room = getFirstFreeRoom(orderBookingDTO);
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
                    .orderStatus(orderStatusService.getById(orderStatusId))
                    .optionals(optionals)
                    .build();
            orderBookingRepository.saveAndFlush(orderBooking);
            return orderBooking;
        } else {
            return null;
        }
    }

    @Override
    public void save(OrderBooking orderBooking) {
        orderBookingRepository.saveAndFlush(orderBooking);
    }


    //free room of a given type for the desired dates
    @Override
    public Room getFirstFreeRoom(OrderBookingDTO orderBookingDTO) {
        var orderBookings = orderBookingRepository.findAll();

        var busyRooms = orderBookings.stream()
                .filter(orderBooking -> orderBooking.getRoom().getRoomKind().getRoomType().getId() == orderBookingDTO.getRoomType())
                .filter(orderBooking -> orderBooking.getRoom().getRoomKind().getClassApartment().getId() == orderBookingDTO.getClassApartment())
                .filter(orderBooking -> !(orderBooking.getDateArrival().isAfter(orderBookingDTO.getDateDeparture()) ||
                        orderBooking.getDateDeparture().isBefore(orderBookingDTO.getDateArrival()) ||
                        orderBooking.getDateArrival().isEqual(orderBookingDTO.getDateDeparture()) ||
                        orderBooking.getDateDeparture().isEqual(orderBookingDTO.getDateArrival())))
                .map(OrderBooking::getRoom)
                .distinct()
                .collect(Collectors.toList());

        var rooms = roomService.getAll().stream()
                .filter(room -> room.getRoomKind().getRoomType().getId() == orderBookingDTO.getRoomType())
                .filter(room -> room.getRoomKind().getClassApartment().getId() == orderBookingDTO.getClassApartment())
                .collect(Collectors.toList());

        Iterator iterator = busyRooms.iterator();
        while (iterator.hasNext()) {
            rooms.remove(iterator.next());
        }

        var roomCheck = rooms.stream().findFirst();

        if (roomCheck.isPresent()){
            return roomCheck.get();
        }
        else return null;
    }

    @Override
    public Map<RoomKind,Long> getFreeRooms(OrderBookingDTO orderBookingDTO) {
        var orderBookings = orderBookingRepository.findAll();
        var rooms = roomService.getAll();

        var busyRooms = orderBookings.stream()
                .filter(orderBooking -> !(orderBooking.getDateArrival().isAfter(orderBookingDTO.getDateDeparture()) ||
                        orderBooking.getDateDeparture().isBefore(orderBookingDTO.getDateArrival()) ||
                        orderBooking.getDateArrival().isEqual(orderBookingDTO.getDateDeparture()) ||
                        orderBooking.getDateDeparture().isEqual(orderBookingDTO.getDateArrival())))
                .map(OrderBooking::getRoom)
                .distinct()
                .collect(Collectors.toList());


        Iterator iterator = busyRooms.iterator();
        while (iterator.hasNext()) {
            rooms.remove(iterator.next());
        }

        var freeRooms = rooms.stream()
                .collect(Collectors.groupingBy(Room::getRoomKind, Collectors.counting()));

        return freeRooms;
    }


    @Override
    public OrderBooking update(OrderBookingDTO orderBookingDTO, OrderBooking orderBooking) {
        var orderBookingUpdate = OrderBooking.builder()
                .id(orderBooking.getId())
                .date(orderBooking.getDate())
                .dateArrival(orderBooking.getDateArrival())
                .dateDeparture(orderBooking.getDateDeparture())
                .quantityPersons(orderBooking.getQuantityPersons())
                .client(orderBooking.getClient())
                .room(orderBooking.getRoom())
                .sumTotal(orderBooking.getSumTotal())
                .orderStatus(orderBooking.getOrderStatus())
                .optionals(orderBooking.getOptionals())
                .build();
        orderBookingRepository.saveAndFlush(orderBookingUpdate);
        return orderBookingUpdate;
    }

    @Override
    public OrderBooking update(OrderBooking orderBooking) {
        var optionals = orderBooking.getOptionals();
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
                .optionals(optionals)
                .build();
        orderBookingRepository.saveAndFlush(orderBookingUpdate);
        return orderBookingUpdate;
    }

}







