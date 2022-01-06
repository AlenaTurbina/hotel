package com.hotel.service.impl;

import com.hotel.dto.ClassApartmentDTO;
import com.hotel.model.repository.ClassApartmentRepository;
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

    @Override
    public ClassApartment getById(Integer id) {
        return classApartmentRepository.getById(id);
    }

    @Override
    public ClassApartment save(ClassApartmentDTO classApartmentDTO) {
        var classApartment = ClassApartment.builder()
                .name(classApartmentDTO.getName())
                .placePrice(classApartmentDTO.getPlacePrice())
                .build();

        return classApartmentRepository.saveAndFlush(classApartment);
    }

    @Override
    public void update(ClassApartment classApartment) {
        classApartmentRepository.saveAndFlush(classApartment);

    }
}
