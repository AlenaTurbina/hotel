package com.hotel.service;

import com.hotel.model.entity.User;

import java.util.List;

public interface UserService {
   List<User> getAll();

   User getById(Integer id);

   void save(User user);
}
