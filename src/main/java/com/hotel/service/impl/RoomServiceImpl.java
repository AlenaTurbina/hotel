package com.hotel.service.impl;

import com.hotel.dto.OrderBookingDTO;
import com.hotel.model.repository.RoomRepository;
import com.hotel.model.entity.Room;
import com.hotel.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService{
    private RoomRepository roomRepository;

    @Override
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room getById(Integer id) {
        return roomRepository.getById(id);
    }

    @Override
    public Room checkRoom(Integer roomTypeId, Integer classApartmentId) {
        var rooms = roomRepository.findAll();
        var checkRoom = rooms.stream().filter(room -> room.getRoomKind().getRoomType().getId() == roomTypeId)
                .filter(room1 -> room1.getRoomKind().getClassApartment().getId() == classApartmentId)
                .findFirst();
        return checkRoom.get();
    }

//На всякий случай 11.12
//    @Override
//    public Room checkRoom(Integer roomTypeId, Integer classApartmentId) {
//        var rooms = roomRepository.findAll();
//        var checkRoom = rooms.stream().filter(room -> room.getRoomType().getId() == roomTypeId)
//                .filter(room1 -> room1.getClassApartment().getId() == classApartmentId)
//                .findFirst();
//        return checkRoom.get();
//    }


//////////29/11
@Override
public Room checkRoomNew(OrderBookingDTO orderBookingDTO) {
    var rooms = roomRepository.findAll();
    var checkRoom = rooms.stream().filter(room -> room.getRoomKind().getRoomType().getId() == orderBookingDTO.getRoomType())
            .filter(room1 -> room1.getRoomKind().getClassApartment().getId() == orderBookingDTO.getClassApartment())
            .findFirst();
    return checkRoom.get();
}




////////////////Process inf

    public Room freeRoom(List<Room> roomsBusy) {
        var roomsAll = roomRepository.findAll();
        var roomFree = roomsAll.stream().filter(rooms -> roomsAll.removeAll(roomsBusy))
                .findFirst();
        return roomFree.get();
    }


    public Room checkRoom1(Integer roomTypeId, Integer classApartmentId) {
        var rooms = roomRepository.findAll();
        var checkRoom = rooms.stream().filter(room -> room.getRoomKind().getRoomType().getId() == roomTypeId)
                .filter(room1 -> room1.getRoomKind().getClassApartment().getId() == classApartmentId)

                .findFirst();
        return checkRoom.get();
    }

}
