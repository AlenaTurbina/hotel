package com.hotel.service;

import com.hotel.dto.UserDTO;
import com.hotel.model.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(Integer id);

    User getByEmail(String email);

    void save(UserDTO userDTO);

    User findLoggedUser(Authentication authentication);

    void update(User user);

}
