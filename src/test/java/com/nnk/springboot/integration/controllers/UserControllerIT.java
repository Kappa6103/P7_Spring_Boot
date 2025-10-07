package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.configuration.SpringSecurityConfig;
import com.nnk.springboot.constant.Const;
import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.models.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Import(SpringSecurityConfig.class)
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateUser() throws Exception {
        final int sizeOfList = userRepository.findAll().size();

        mockMvc.perform(post("/user/validate")
                        .param("username", "testuser")
                        .param("password", "password123")
                        .param("fullname", "Test User")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        // Verify user was created in database
        List<User> userList = userRepository.findAll();
        User user = userList.getFirst();
        System.out.println(user.getFullname());
        assertEquals("testuser",user.getUsername());
        assertEquals("Test User", user.getFullname());
        assertEquals("USER", user.getRole().toString());

        assertEquals(sizeOfList + 1, userList.size());

        assertNotNull(user.getPassword());
        assertEquals(Const.PWD_HASHED_SIZE, user.getPassword().length());
        assertFalse("password123".equals(user.getPassword()));
    }

    @Test
    @Disabled
    void whenInvalidInput_thenShowErrors() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("username", "")  // Invalid: blank username
                        .param("password", "pwd")
                        .param("fullname", "Test")
                        .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().hasErrors());
    }

}
