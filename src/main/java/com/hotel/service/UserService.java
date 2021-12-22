package com.hotel.service;

import com.hotel.dto.UserDTO;
import com.hotel.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
   List<User> getAll();

   User getById(Integer id);

//   User save(UserDTO userDTO);

   User getByEmail(String email);

   void save1(UserDTO userDTO);




}
