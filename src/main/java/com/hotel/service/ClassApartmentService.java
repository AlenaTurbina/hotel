package com.hotel.service;

import com.hotel.dto.ClassApartmentDTO;
import com.hotel.model.entity.ClassApartment;

import java.util.List;

public interface ClassApartmentService {
    List<ClassApartment> getAll();

    ClassApartment getById(Integer id);

    ClassApartment getByName(String name);

    ClassApartment save(ClassApartmentDTO classApartmentDTO);

    void update(ClassApartment classApartment);
}
