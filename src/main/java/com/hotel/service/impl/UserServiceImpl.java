package com.hotel.service.impl;

import com.hotel.model.dao.UserRepository;
import com.hotel.model.entity.User;
import com.hotel.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Integer id) {
        return userRepository.getById(id);
    }

    @Override
    public void save(User user) {
        userRepository.saveAndFlush(user);
    }
}
