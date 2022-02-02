package com.hotel.service.impl;

import com.hotel.dto.UserDTO;
import com.hotel.model.entity.Role;
import com.hotel.model.repository.UserRepository;
import com.hotel.model.entity.User;
import com.hotel.service.RoleService;
import com.hotel.service.UserService;
import com.hotel.service.UserStatusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import java.util.List;

import static com.hotel.utilit.Constant.ID_DEFAULT_ROLE_CLIENT;
import static com.hotel.utilit.Constant.ID_DEFAULT_USER_STATUS_ACTIVE;


@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleService roleService;
    private UserStatusService userStatusService;
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        log.info("User getAll");
        return userRepository.findAll();
    }

    @Override
    public User getById(Integer id) {
        var user = userRepository.getById(id);
        if (user != null) {
            log.info("User getById is not null (id): " + id);
            return user;
        } else {
            log.info("User getById is null(id): " + id);
            return null;
        }
    }

    @Override
    public User getByEmail(String email) {
        var user = userRepository.findByEmail(email);
        if(user !=null){
            log.info("User was find (user email): " +  email);
            return user;
        }
        log.info("User doesn't exist (user email): " +  email);
        return null;
    }

    @Override
    @Transactional
    public User saveAndReturn(UserDTO userDTO) {
        List<Role> roles = new ArrayList<>(List.of(roleService.getById(ID_DEFAULT_ROLE_CLIENT)));
        var password = passwordEncoder.encode(userDTO.getPassword());
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
        var userSave = userRepository.saveAndFlush(user);
        log.info("User was saved (user id): " +  userSave.getId());
        return userSave;
    }

    @Override
    public User findLoggedUser(Authentication authentication) {
        var user = getByEmail(authentication.getName());
        log.info("Logged user is (id): " +  user.getId());
        return user;
    }

    @Override
    @Transactional
    public void update(User user) {
        userRepository.saveAndFlush(user);
        log.info("User was updated (id): " +  user.getId());
    }

    @Override
    @Transactional
    public User update(Integer id, String firstName, String lastName, String document, String phoneNumber, String password, String email) {
        var userUpdate = userRepository.findById(id).get();
        userUpdate.setFirstName(firstName);
        userUpdate.setLastName(lastName);
        userUpdate.setDocument(document);
        userUpdate.setPhoneNumber(phoneNumber);
        userUpdate.setPassword(passwordEncoder.encode(password));
        userUpdate.setEmail(email);
        log.info("User update with set-fields (user id): " +  id);
        return  userRepository.saveAndFlush(userUpdate);
    }

    @Override
    public Page<User> findPaginated(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        log.info("User getAll with pages");
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findPaginatedAndSorted(Integer pageNo, Integer pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        log.info("User getAll with pages and sorting");
        return userRepository.findAll(pageable);
    }

}
