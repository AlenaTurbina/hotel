package com.hotel.service.impl;

import com.hotel.model.dao.ClassApartmentRepository;
import com.hotel.model.entity.ClassApartment;
import com.hotel.service.ClassApartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClassApartmentServiceImpl implements ClassApartmentService {
    private ClassApartmentRepository classApartmentRepository;

    @Override
    public List<ClassApartment> getAll() {
        return classApartmentRepository.findAll();
    }
}
