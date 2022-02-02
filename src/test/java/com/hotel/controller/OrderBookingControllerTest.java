package com.hotel.controller;

import com.hotel.configuration.TestConfigurationUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class OrderBookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderBookingController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void testOrderBookings() throws Exception {
        this.mockMvc.perform(get("/admin/orderBookings"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void testOrderForm() throws Exception {
        this.mockMvc.perform(get("/client/orderForms"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void testFreeRoomsForm() throws Exception {
        this.mockMvc.perform(get("/home/freeRoomForms"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/home/freeRoomForms"));
    }

    @Test
    void testFreeRoomsFormAdmin() throws Exception {
        this.mockMvc.perform(get("/admin/freeRoomFormsAdmin"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void testUpdateOrderBookingsForm() throws Exception {
        this.mockMvc.perform(get("/admin/orderBookings/update"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }


}