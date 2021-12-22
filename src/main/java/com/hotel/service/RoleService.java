package com.hotel.service;

import com.hotel.model.entity.Role;

import java.util.List;

public interface RoleService {
   List<Role> getAll();

   Role getById(Integer id);
}
