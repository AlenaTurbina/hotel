package com.hotel.service.impl;

import com.hotel.model.repository.UserStatusRepository;
import com.hotel.model.entity.UserStatus;
import com.hotel.service.UserStatusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserStatusServiceImpl implements UserStatusService {
    private UserStatusRepository userStatusRepository;

    @Override
    public List<UserStatus> getAll() {
        log.info("UserStatus getAll");
        return userStatusRepository.findAll();
    }

    @Override
    public UserStatus getById(Integer id) {
        var userStatus = userStatusRepository.getById(id);
        if (userStatus != null) {
            log.info("UserStatus getById is not null (id): " + id);
            return userStatus;
        } else {
            log.info("UserStatus getById is null(id): " + id);
            return null;
        }
    }
}
