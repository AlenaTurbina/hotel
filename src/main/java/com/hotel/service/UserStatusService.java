package com.hotel.service;

import com.hotel.model.entity.UserStatus;

import java.util.List;

public interface UserStatusService {
    List<UserStatus> getAll();

    UserStatus getById(Integer id);
}
