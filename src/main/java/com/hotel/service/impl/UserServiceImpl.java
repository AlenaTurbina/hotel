package com.hotel.service.impl;

import com.hotel.dto.UserDTO;
import com.hotel.model.entity.Role;
import com.hotel.model.repository.UserRepository;
import com.hotel.model.entity.User;
import com.hotel.service.RoleService;
import com.hotel.service.UserService;
import com.hotel.service.UserStatusService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;

import static com.hotel.utilit.Constant.ID_DEFAULT_ROLE_CLIENT;
import static com.hotel.utilit.Constant.ID_DEFAULT_USER_STATUS_ACTIVE;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleService roleService;
    private UserStatusService userStatusService;
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Integer id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByEmail(String email) {
        var user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public void save(UserDTO userDTO) {
        var password = passwordEncoder.encode(userDTO.getPassword());
        List<Role> roles = new ArrayList<>(List.of(roleService.getById(ID_DEFAULT_ROLE_CLIENT)));
        var user = User.builder()
                .email(userDTO.getEmail())
                .password(password)
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phoneNumber(userDTO.getPhoneNumber())
                .document(userDTO.getDocument())
                .roles(roles)
                .userStatus(userStatusService.getById(ID_DEFAULT_USER_STATUS_ACTIVE))
                .build();
        userRepository.saveAndFlush(user);
    }

    @Override
    public User findLoggedUser(Authentication authentication) {
        var user = getByEmail(authentication.getName());
        return user;
    }

    @Override
    public void update(User user) {
        userRepository.saveAndFlush(user);
    }
}
