package com.hotel.service.impl;

import com.hotel.dto.UserDTO;
import com.hotel.model.entity.Role;
import com.hotel.model.repository.UserRepository;
import com.hotel.model.entity.User;
import com.hotel.service.RoleService;
import com.hotel.service.UserService;
import com.hotel.service.UserStatusService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleService roleService;
    private UserStatusService userStatusService;

    private final Integer clientRoleId = 2;     //default value for new user: role = "CLIENT"
    private final Integer clientStatusId = 1;   //default value for new user: status = "ACTIVE"

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
    public void save1(UserDTO userDTO) {
        var password = passwordEncoder.encode(userDTO.getPassword());
        List<Role> roles = new ArrayList<>(List.of(roleService.getById(clientRoleId)));
        var user = User.builder()
                .email(userDTO.getEmail())
                .password(password)
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .roles(roles)
                .userStatus(userStatusService.getById(clientStatusId))
                .build();
        userRepository.saveAndFlush(user);
    }

//User --> UserDetails
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }



}
