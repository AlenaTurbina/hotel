package com.hotel.service.impl;

import com.hotel.model.repository.UserStatusRepository;
import com.hotel.model.entity.UserStatus;
import com.hotel.service.UserStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserStatusServiceImpl implements UserStatusService {
    private UserStatusRepository userStatusRepository;

    @Override
    public List<UserStatus> getAll() {
        return userStatusRepository.findAll();
    }

    @Override
    public UserStatus getById(Integer id) {
        return userStatusRepository.getById(id);
    }
}
