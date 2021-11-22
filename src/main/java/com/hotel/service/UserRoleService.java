package com.hotel.service;

import com.hotel.model.entity.UserRole;

import java.util.List;

public interface UserRoleService {
   List<UserRole> getAll();

   UserRole getById(Integer id);
}
