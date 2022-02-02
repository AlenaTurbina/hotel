package com.hotel.service.impl;

import com.hotel.dto.RoomKindDTO;
import com.hotel.model.entity.ClassApartment;
import com.hotel.model.entity.RoomKind;
import com.hotel.model.entity.RoomType;
import com.hotel.model.repository.RoomKindRepository;
import com.hotel.service.ClassApartmentService;
import com.hotel.service.RoomKindService;
import com.hotel.service.RoomTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RoomKindServiceImpl implements RoomKindService {
    private RoomKindRepository roomKindRepository;
    private ClassApartmentService classApartmentService;
    private RoomTypeService roomTypeService;

    @Override
    public List<RoomKind> getAll() {
        log.info("RoomKind getAll");
        return roomKindRepository.findAll();
    }

    @Override
    public RoomKind getById(Integer id) {
        var roomKind = roomKindRepository.getById(id);
        if (roomKind != null) {
            log.info("RoomKind getById is not null (id): " + id);
            return roomKind;
        } else {
            log.info("RoomKind getById is null(id): " + id);
            return null;
        }
    }

    @Override
    public List<ClassApartment> getListUniqueClassApartmentsFromRoomKinds() {
        log.info("Get list unique class apartments from room kinds");
        return roomKindRepository.findAll().stream()
                .map(roomKind ->roomKind.getClassApartment())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomType> getListUniqueRoomTypesFromRoomKinds() {
        log.info("Get list unique room types from room kinds");
        return roomKindRepository.findAll().stream()
                .map(roomKind ->roomKind.getRoomType())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Integer getRoomKindByRoomTypeAndClassApartmentID(Integer roomTypeID, Integer classApartmentID) {
        log.info("Get room Kind by (roomTypeID, ClassApartmentID): " + roomTypeID + ", " + classApartmentID);
        var roomKinds = roomKindRepository.findAll();

        Integer resultID = null;
        for (var roomKind:roomKinds) {
            if(roomTypeID == roomKind.getRoomType().getId() && classApartmentID == roomKind.getClassApartment().getId()){
                resultID = roomKind.getId();
            }
        }
        log.info("Result of getting room Kind by (roomTypeID, ClassApartmentID, result roomKindID): "
                + roomTypeID + ", " + classApartmentID + ", " + resultID);
        return resultID;
    }

    @Override
    @Transactional
    public RoomKind save(RoomKindDTO roomKindDTO) {
        var roomKind = new RoomKind();
        roomKind.setRoomType(roomTypeService.getById(roomKindDTO.getRoomType()));
        roomKind.setClassApartment(classApartmentService.getById(roomKindDTO.getClassApartment()));
        roomKind.setRoomPrice(roomKindDTO.getRoomPrice());
        log.info("New room kind set (roomType, classApartment, price): " + roomKindDTO.getRoomType() + ", "
                + roomKindDTO.getClassApartment() + ", " + roomKindDTO.getRoomPrice());
        return roomKindRepository.saveAndFlush(roomKind);
    }

    @Override
    @Transactional
    public void update(Integer id, RoomType roomType, ClassApartment classApartment, Double roomPrice) {
        var roomKindUpdate = roomKindRepository.findById(id);
        var roomKindNew = roomKindUpdate.get();
        roomKindNew.setRoomType(roomType);
        roomKindNew.setClassApartment(classApartment);
        roomKindNew.setRoomPrice(roomPrice);
        log.info("Room kind update (id): " + id);
        roomKindRepository.saveAndFlush(roomKindNew);
    }

}
