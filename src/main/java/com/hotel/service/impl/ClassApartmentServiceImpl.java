package com.hotel.service.impl;

import com.hotel.dto.ClassApartmentDTO;
import com.hotel.model.repository.ClassApartmentRepository;
import com.hotel.model.entity.ClassApartment;
import com.hotel.service.ClassApartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ClassApartmentServiceImpl implements ClassApartmentService {
    private ClassApartmentRepository classApartmentRepository;

    @Override
    public List<ClassApartment> getAll() {
        log.info("Class apartment getAll");
        return classApartmentRepository.findAll();
    }

    @Override
    public ClassApartment getByName(String name) {
        var classApartment = classApartmentRepository.findClassApartmentByName(name);
        if (classApartment != null) {
            log.info("Class apartment getByName is not null (name): " + name);
            return classApartment;
        } else {
            log.info("Class apartment getByName is null (name): " + name);
            return null;
        }
    }

    @Override
    public ClassApartment getById(Integer id) {
        var classApartment = classApartmentRepository.getById(id);
        if (classApartment != null) {
            log.info("Class apartment getById is not null (id): " + id);
            return classApartment;
        } else {
            log.info("Class apartment getById is null (id): " + id);
            return null;
        }
    }

    @Override
    @Transactional
    public ClassApartment save(ClassApartmentDTO classApartmentDTO) {
        var classApartment = ClassApartment.builder()
                .name(classApartmentDTO.getName())
                .placePrice(classApartmentDTO.getPlacePrice())
                .build();
        log.info("New ClassApartment build (name, price): " + classApartmentDTO.getName() + " , " + classApartmentDTO.getPlacePrice());
        return classApartmentRepository.saveAndFlush(classApartment);
    }

    @Override
    @Transactional
    public void update(ClassApartment classApartment) {
        classApartmentRepository.saveAndFlush(classApartment);
        log.info("Class apartment update (id): " + classApartment.getId());
    }
}
