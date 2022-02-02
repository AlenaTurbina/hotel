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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestConfigurationUserDetails.class
)
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestConfigurationUserDetails.class)
class RoomKindControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomKindController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void testRoomKinds() throws Exception {
        this.mockMvc.perform(get("/admin/roomKinds"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void testCreateRoomKindsForm() throws Exception {
        this.mockMvc.perform(get("/admin/roomKinds/create"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }


    @Test
    void testUpdateRoomKindsForm()  throws Exception {
        this.mockMvc.perform(get("/admin/roomKinds/update"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testRoomKindsWithRoleAdmin() throws Exception {
        this.mockMvc.perform(get("/admin/roomKinds"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("client@test.com")
    void testRoomKindsWithRoleClient() throws Exception {
        this.mockMvc.perform(get("/admin/roomKinds"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testRoomsCreateWithRoleAdmin() throws Exception {
        this.mockMvc.perform(get("/admin/roomKinds/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/createRoomKinds"));
    }

    @Test
    @WithUserDetails("client@test.com")
    void testRoomsCreateWithRoleClient() throws Exception {
        this.mockMvc.perform(get("/admin/roomKinds/create"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}