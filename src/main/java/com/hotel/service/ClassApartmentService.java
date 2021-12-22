package com.hotel.service;

import com.hotel.dto.ClassApartmentDTO;
import com.hotel.model.entity.ClassApartment;

import java.util.List;

public interface ClassApartmentService {
    List<ClassApartment> getAll();

    ClassApartment getById(Integer id);

    ClassApartment saveByDTO(ClassApartmentDTO classApartmentDTO);

    void save(ClassApartment classApartment);
}
