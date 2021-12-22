package com.hotel.service.impl;

import com.hotel.model.repository.OptionalRepository;
import com.hotel.model.entity.Optional;
import com.hotel.service.OptionalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OptionalServiceImpl implements OptionalService {
    private OptionalRepository optionalRepository;

    @Override
    public List<Optional> getAll() {
        return optionalRepository.findAll();
    }

    @Override
    public Optional getById(Integer id) {
        return optionalRepository.getById(id);
    }

    @Override
    public List<Optional> getListById(List<Integer> ids) {
        List<Optional> optionals = new ArrayList<>();
        for (Integer i:ids) {
            optionals.add(optionalRepository.getById(i));
        }
        return optionals;
    }

}
