package com.hotel.service.impl;

import com.hotel.dto.OptionalDTO;
import com.hotel.model.repository.OptionalRepository;
import com.hotel.model.entity.Optional;
import com.hotel.service.OptionalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OptionalServiceImpl implements OptionalService {
    private OptionalRepository optionalRepository;

    @Override
    public List<Optional> getAll() {
        log.info("Optional getAll");
        return optionalRepository.findAll();
    }

    @Override
    public Optional getByName(String name) {
        var optional = optionalRepository.findOptionalByName(name);
        if (optional != null) {
            log.info("Optional getByName is not null (name): " + name);
            return optional;
        } else {
            log.info("Optional getByName is null (name): " + name);
            return null;
        }
    }

    @Override
    public Optional getById(Integer id) {
        var optional = optionalRepository.getById(id);
        if (optional != null) {
            log.info("Optional getById is not null (id): " + id);
            return optional;
        } else {
            log.info("Optional getById is null(id): " + id);
            return null;
        }
    }

    @Override
    public List<Optional> getListById(List<Integer> ids) {
        List<Optional> optionals = new ArrayList<>();
        for (Integer id:ids) {
            optionals.add(optionalRepository.getById(id));
            log.info("Optional getListById add optional (id): " + id);
        }
        return optionals;
    }

    @Override
    @Transactional
    public Optional save(OptionalDTO optionalDTO) {
        var optional = Optional.builder()
                .name(optionalDTO.getName())
                .optionalPrice(optionalDTO.getOptionalPrice())
                .build();
        log.info("New Optional build (name, price): " + optionalDTO.getName() + ", " + optionalDTO.getOptionalPrice());
        return optionalRepository.saveAndFlush(optional);
    }

    @Override
    @Transactional
    public void update(Optional optional) {
        optionalRepository.saveAndFlush(optional);
        log.info("Optional update (id): " + optional.getId());
    }

}
