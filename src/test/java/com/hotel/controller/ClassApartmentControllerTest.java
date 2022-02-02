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


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestConfigurationUserDetails.class
)
@AutoConfigureMockMvc
class ClassApartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClassApartmentController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void testClassApartments() throws Exception {
        this.mockMvc.perform(get("/admin/classApartments"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void testCreateClassApartmentsForm() throws Exception {
        this.mockMvc.perform(get("/admin/classApartments/create"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void testSaveClassApartments() throws Exception {
        this.mockMvc.perform(get("/admin/classApartments/create"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
        ;
    }

    @Test
    void testUpdateClassApartmentsForm() throws Exception {
        this.mockMvc.perform(get("/admin/classApartments/update"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
        ;
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testClassApartmentsWithRoleAdmin() throws Exception {
        this.mockMvc.perform(get("/admin/classApartments"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("client@test.com")
    void testClassApartmentsWithRoleClient() throws Exception {
        this.mockMvc.perform(get("/admin/classApartments"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testClassApartmentsCreateWithRoleAdmin() throws Exception {
        this.mockMvc.perform(get("/admin/classApartments/create"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("client@test.com")
    void testClassApartmentsCreateWithRoleClient() throws Exception {
        this.mockMvc.perform(get("/admin/classApartments/create"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}