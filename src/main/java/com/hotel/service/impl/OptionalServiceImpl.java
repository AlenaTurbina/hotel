package com.hotel.service.impl;

import com.hotel.model.dao.OptionalRepository;
import com.hotel.model.entity.Optional;
import com.hotel.service.OptionalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OptionalServiceImpl implements OptionalService {
    OptionalRepository optionalRepository;

    @Override
    public List<Optional> getAll() {
        return optionalRepository.findAll();
    }
}
