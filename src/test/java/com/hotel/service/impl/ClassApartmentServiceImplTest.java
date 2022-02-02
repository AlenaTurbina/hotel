package com.hotel.service.impl;

import com.hotel.model.entity.ClassApartment;
import com.hotel.model.repository.ClassApartmentRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(SpringExtension.class)
class ClassApartmentServiceImplTest {
    @Mock
    private ClassApartmentRepository classApartmentRepository;

    @InjectMocks
    private ClassApartmentServiceImpl classApartmentService;

    @Test
    void testGetAll() {
        Integer id1 = 1;
        Integer id2 = 2;
        Integer id3 = 3;
        ClassApartment classApartment1 = new ClassApartment(id1, null, null);
        ClassApartment classApartment2 = new ClassApartment(id2, null, null);
        ClassApartment classApartment3 = new ClassApartment(id3, null, null);

        List<ClassApartment> classApartments = new ArrayList<>(List.of(classApartment1, classApartment2, classApartment3));
        Mockito.when(classApartmentRepository.findAll()).thenReturn(classApartments);
        List<ClassApartment> classApartmentsExpected = new ArrayList<>(List.of(classApartment1, classApartment2, classApartment3));
        List<ClassApartment> classApartmentsActual = classApartmentService.getAll();

        Assert.assertEquals(classApartmentsExpected, classApartmentsActual);
    }

    @Test
    void testGetById() {
        Integer id = 10;
        ClassApartment classApartment = new ClassApartment(id, null, null);
        Mockito.when(classApartmentRepository.getById(any())).thenReturn(classApartment);
        ClassApartment classApartmentTest = classApartmentService.getById(id);

        Assert.assertEquals(id, classApartmentTest.getId());
    }

    @Test
    void testGetByName() {
        ClassApartment classApartment1 = new ClassApartment(1, "CA1", null);
        Mockito.when(classApartmentRepository.findClassApartmentByName(any())).thenReturn(classApartment1);

        Assert.assertEquals(classApartment1, classApartmentService.getByName("CA1"));
    }

}