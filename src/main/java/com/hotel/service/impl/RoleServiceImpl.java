package com.hotel.service.impl;

import com.hotel.model.entity.Role;
import com.hotel.model.repository.RoleRepository;
import com.hotel.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        log.info("Role getAll");
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Integer id) {
        var role = roleRepository.getById(id);
        if (role != null) {
            log.info("Role getById is not null (id): " + id);
            return role;
        } else {
            log.info("Role getById is null(id): " + id);
            return null;
        }
    }
}
