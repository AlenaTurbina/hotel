package com.hotel.service.impl;

import com.hotel.model.dao.UserRoleRepository;
import com.hotel.model.entity.UserRole;
import com.hotel.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> getAll() {

        return userRoleRepository.findAll();
    }

    @Override
    public UserRole getById(Integer id) {
        return userRoleRepository.getById(id);
    }
}
