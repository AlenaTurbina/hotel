package com.hotel.service.impl;

import com.hotel.model.entity.User;
import com.hotel.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    User user1 = new User(1, "user1@test.com", null, null, null, null, null, null, null, null);
    User user2 = new User(2, "user2@test.com", null, null, null, null, null, null, null, null);
    User user3 = new User(3, "user3@test.com", null, null, null, null, null, null, null, null);

    @Test
    void testGetAll() {
        List<User> users = new ArrayList<>(List.of(user1, user2, user3));
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<User> userExpected = new ArrayList<>(List.of(user1, user2, user3));
        List<User> userActual = userService.getAll();

        assertEquals(userExpected, userActual);

    }

    @Test
    void testGetById() {
        Integer id = 1;
        Mockito.when(userRepository.getById(any())).thenReturn(user1);
        User userTest = userService.getById(id);

        assertEquals(id, userTest.getId());
    }

    @Test
    void testGetByEmail() {
        String email = "user1@test.com";
        Mockito.when(userRepository.findByEmail(any())).thenReturn(user1);
        User userTest = userService.getByEmail(email);

        assertEquals(email, userTest.getEmail());
    }

}