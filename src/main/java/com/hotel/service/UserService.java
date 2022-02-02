package com.hotel.service;

import com.hotel.dto.UserDTO;
import com.hotel.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(Integer id);

    User getByEmail(String email);

    User saveAndReturn(UserDTO userDTO);

    User findLoggedUser(Authentication authentication);

    void update(User user);

    User update(Integer id, String firstName, String lastName, String document, String phoneNumber, String password, String email);

    Page<User> findPaginated(Integer pageNo, Integer pageSize);

    Page<User> findPaginatedAndSorted(Integer pageNo, Integer pageSize, String sortField, String sortDirection);

}
