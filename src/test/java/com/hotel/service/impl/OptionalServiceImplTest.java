package com.hotel.service.impl;

import com.hotel.model.entity.Optional;
import com.hotel.model.repository.OptionalRepository;
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
class OptionalServiceImplTest {
    @Mock
    private OptionalRepository optionalRepository;
    @InjectMocks
    private OptionalServiceImpl optionalService;

    private Optional optional;

    @Test
    void testGetAll() {
        Integer id1 = 1;
        Integer id2 = 2;
        Integer id3 = 3;
        Integer id4 = 4;
        Optional optional1 = new Optional(id1, null, null);
        Optional optional2 = new Optional(id2, null, null);
        Optional optional3 = new Optional(id3, null, null);
        Optional optional4 = new Optional(id4, null, null);

        List<Optional> optionals = new ArrayList<>(List.of(optional1, optional2, optional3, optional4));
        Mockito.when(optionalRepository.findAll()).thenReturn(optionals);
        List<Optional> optionalExpected = new ArrayList<>(List.of(optional1, optional2, optional3, optional4));
        List<Optional> optionalActual = optionalService.getAll();

        Assert.assertEquals(optionalExpected, optionalActual);
    }

    @Test
    void testGetById() {
        Integer id = 1;
        optional = new Optional(id, null, null);
        Mockito.when(optionalRepository.getById(any())).thenReturn(optional);
        Optional optionalActual = optionalService.getById(id);

        Assert.assertEquals(id, optionalActual.getId());
    }

    @Test
    void testGetListById() {
        Integer id1 = 1;
        Integer id2 = 2;
        Integer id3 = 3;
        Integer id4 = 4;
        Optional optional1 = new Optional(id1, null, 1.0);
        Optional optional2 = new Optional(id2, null, 2.0);
        Optional optional3 = new Optional(id3, null, 3.0);
        //failed optional
        Optional optional4 = new Optional(id4, null, 4.0);

        List<Integer> optionalsID = new ArrayList<>(List.of(id1, id2, id3));
        Mockito.when(optionalRepository.getById(id1)).thenReturn(optional1);
        Mockito.when(optionalRepository.getById(id2)).thenReturn(optional2);
        Mockito.when(optionalRepository.getById(id3)).thenReturn(optional3);
        Mockito.when(optionalRepository.getById(id4)).thenReturn(optional4);

        List<Optional> optionalExpected = new ArrayList<>(List.of(optional1, optional2, optional3));
        List<Optional> optionalActual = optionalService.getListById(optionalsID);

        Assert.assertEquals(optionalExpected, optionalActual);
    }

    @Test
    void testGetByName() {
        Optional optional1 = new Optional(1, "Opt1", null);
        Optional optional2 = new Optional(2, "Opt2", null);
        Mockito.when(optionalRepository.findOptionalByName(any())).thenReturn(optional1);

        Assert.assertEquals(optional1, optionalService.getByName("Opt1"));
    }

}