package com.hotel.controller;

import com.hotel.configuration.TestConfigurationUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestConfigurationUserDetails.class
)
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestConfigurationUserDetails.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void testRoles() throws Exception {
        this.mockMvc.perform(get("/admin/roles"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testRolesWithRoleAdmin() throws Exception {
        this.mockMvc.perform(get("/admin/roles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/roles"));
    }

    @Test
    @WithUserDetails("client@test.com")
    void testRolesWithRoleClient() throws Exception {
        this.mockMvc.perform(get("/admin/roles"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}