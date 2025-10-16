package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.constant.Const;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void home_getRequest_notLogged_in() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void adminHome_getRequest_notLogged_in() throws Exception {
        mockMvc.perform(get("/admin/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

    }

    @Test
    void home_getRequest() throws Exception {
        mockMvc.perform(get("/")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    void adminHome_getRequest() throws Exception {
        mockMvc.perform(get("/admin/home")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

    }

}