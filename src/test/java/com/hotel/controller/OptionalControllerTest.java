package com.hotel.controller;

import com.hotel.configuration.TestConfigurationUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
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
class OptionalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OptionalController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void testOptionals() throws Exception {
        this.mockMvc.perform(get("/admin/optionals"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void testCreateOptionalsForm() throws Exception {
        this.mockMvc.perform(get("/admin/optionals/create"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void testSaveOptionals() throws Exception {
        this.mockMvc.perform(get("/admin/optionals/create"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void testUpdateOptionalsForm() throws Exception {
        this.mockMvc.perform(get("/admin/optionals/update"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testOptionalsWithRoleAdmin() throws Exception {
        this.mockMvc.perform(get("/admin/optionals"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("client@test.com")
    void testOptionalsWithRoleClient() throws Exception {
        this.mockMvc.perform(get("/admin/optionals"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testOptionalsCreateWithRoleAdmin() throws Exception {
        this.mockMvc.perform(get("/admin/optionals/create"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("client@test.com")
    void testOptionalsCreateWithRoleClient() throws Exception {
        this.mockMvc.perform(get("/admin/optionals/create"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}