package com.hotel.service.impl;

import com.hotel.model.entity.Role;
import com.hotel.model.repository.RoleRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;

    @Test
    void testGetAll() {
        Integer id1 = 1;
        Integer id2 = 2;
        Integer id3 = 3;
        Role role1 = new Role(id1, null);
        Role role2 = new Role(id2, null);
        Role role3 = new Role(id3, null);

        List<Role> roles = new ArrayList<>(List.of(role1, role2, role3));
        Mockito.when(roleRepository.findAll()).thenReturn(roles);
        List<Role> rolesExpected = new ArrayList<>(List.of(role1, role2, role3));
        List<Role> rolesActual = roleService.getAll();

        Assert.assertEquals(rolesExpected, rolesActual);
    }

    @Test
    void testGetById() {
        Integer id = 10;
        role = new Role(id, null);
        Mockito.when(roleRepository.getById(any())).thenReturn(role);
        Role roleTest = roleService.getById(id);

        Assert.assertEquals(id, roleTest.getId());
    }
}