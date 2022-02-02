package com.hotel.service;

import com.hotel.dto.OptionalDTO;
import com.hotel.model.entity.Optional;

import java.util.List;

public interface OptionalService {
    List<Optional> getAll();

    Optional getById(Integer id);

    Optional getByName(String name);

    List<Optional> getListById(List<Integer> ids);

    void update(Optional optional);

    Optional save(OptionalDTO optionalDTO);
}
