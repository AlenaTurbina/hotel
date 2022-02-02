package com.hotel.service.impl;

import com.hotel.model.entity.UserStatus;
import com.hotel.model.repository.UserStatusRepository;
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
class UserStatusServiceImplTest {
    @Mock
    UserStatusRepository userStatusRepository;

    @InjectMocks
    UserStatusServiceImpl userStatusService;

    @Test
    void testGetAll() {
        Integer id1 = 1;
        Integer id2 = 2;
        Integer id3 = 3;
        UserStatus userStatus1 = new UserStatus(id1, null);
        UserStatus userStatus2 = new UserStatus(id2, null);
        UserStatus userStatus3 = new UserStatus(id3, null);

        List<UserStatus> userStatuses = new ArrayList<>(List.of(userStatus1, userStatus2, userStatus3));
        Mockito.when(userStatusRepository.findAll()).thenReturn(userStatuses);
        List<UserStatus> userStatusesExpected = new ArrayList<>(List.of(userStatus1, userStatus2, userStatus3));
        List<UserStatus> userStatusesActual = userStatusService.getAll();

        Assert.assertEquals(userStatusesExpected, userStatusesActual);
    }

    @Test
    void testGetById() {
        Integer id = 10;
        UserStatus userStatus = new UserStatus(id, null);
        Mockito.when(userStatusRepository.getById(any())).thenReturn(userStatus);
        UserStatus userStatusTest = userStatusService.getById(id);

        Assert.assertEquals(id, userStatusTest.getId());
    }
}