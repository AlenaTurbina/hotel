package com.hotel.service;

import com.hotel.model.entity.Optional;

import java.util.List;

public interface OptionalService {
    List<Optional> getAll();

    Optional getById(Integer id);

    List<Optional> getListById(List<Integer> ids);
}
