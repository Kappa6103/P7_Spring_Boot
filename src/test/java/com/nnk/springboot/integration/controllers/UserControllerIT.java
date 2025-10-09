package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.User;
import com.nnk.springboot.models.config.Role;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
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

    private void populateDBWithUsers(int users) {
        for (int i = 0; i < users; i++) {
            User user = new User();
            user.setUsername(Const.USERNAME + i);
            user.setPassword(Const.PWD + i);
            user.setFullname(Const.FULLNAME + i);
            user.setRole(Role.valueOf(i % 2 == 0 ? Const.ROLE_USER : Const.ROLE_ADMIN));

            userRepository.save(user);

            System.out.printf("User n%d was created\n", i);
            System.out.printf("username : %s\n", user.getUsername());
            System.out.printf("pwd : %s\n", user.getPassword());
            System.out.printf("fullname : %s\n", user.getFullname());
            System.out.printf("role : %s\n", user.getRole().toString());
        }
    }

    private void populateDBWithOneUser() {
        User user = new User();
        user.setUsername(Const.USERNAME);
        user.setPassword(Const.PWD);
        user.setFullname(Const.FULLNAME);
        user.setRole(Role.valueOf(Const.ROLE_USER));

        userRepository.save(user);
    }

    private int getFirstValidId() {
        List<User> userList = userRepository.findAll();
        if (userList.size() == 0) {
            return -1;
        } else {
            return userList.getFirst().getId();
        }
    }

    @Test
    void whenValidInput_thenCreateUser() throws Exception {
        final int sizeOfList = userRepository.findAll().size();

        mockMvc.perform(post("/user/validate")
                        .param("username", Const.USERNAME)
                        .param("password", Const.PWD)
                        .param("fullname", Const.FULLNAME)
                        .param("role", Const.ROLE_USER))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        // Verify user was created in database
        List<User> userList = userRepository.findAll();
        User user = userList.getFirst();

        assertEquals(Const.USERNAME,user.getUsername());
        assertEquals(Const.FULLNAME, user.getFullname());
        assertEquals(Const.ROLE_USER, user.getRole().toString());

        assertEquals(sizeOfList + 1, userList.size());

        assertNotNull(user.getPassword());
        assertEquals(Const.PWD_HASHED_SIZE, user.getPassword().length());
        assertFalse("password123".equals(user.getPassword()));
    }

    @Test
    void whenInvalidInput_thenShowErrors() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("username", Const.EMPTY_FIELD)
                        .param("password", Const.PWD)
                        .param("fullname", Const.FULLNAME)
                        .param("role", Const.ROLE_USER))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    void homeWithUsers_shouldReturnPopulatedList() throws Exception {
        populateDBWithUsers(100);

        MvcResult result = mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))
                .andReturn();
        List<User> userList = (List<User>) result.getModelAndView().getModel().get("users");
        assertEquals(100, userList.size());
    }

    @Test
    void homeWithoutUsers_shouldReturnEmptyList() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))
                .andReturn();
        List<User> userList = (List<User>) result.getModelAndView().getModel().get("users");
        assertEquals(0, userList.size());
    }

    @Test
    void addUserGetMapping() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void showUpdateForm_valid_id() throws Exception {
        populateDBWithUsers(1);
        int userId = getFirstValidId();
        mockMvc.perform(get("/user/update/" + userId))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"));
    }


    @Test
    void showUpdateForm_invalid_id() throws Exception {

        int nonExistentId = -1;

        Exception exception = assertThrows(ServletException.class,() -> {
            mockMvc.perform(get("/user/update/" + nonExistentId));
        });

        String expectedMessage = "Invalid user Id: " + nonExistentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateUser_valid_user() throws Exception {
        populateDBWithOneUser();
        int userId = getFirstValidId();

        mockMvc.perform(post("/user/update/" + userId)
                        .param("username", Const.USERNAME_UPDATED)
                        .param("password", Const.PWD)
                        .param("fullname", Const.FULLNAME)
                        .param("role", Const.ROLE_USER))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        User user = userRepository.findById(userId).get();

        assertEquals(Const.USERNAME_UPDATED, user.getUsername());
    }

    @Test
    void updateUser_invalid_user() throws Exception {
        populateDBWithOneUser();
        int userId = getFirstValidId();

        mockMvc.perform(post("/user/update/1")
                        .param("username", Const.EMPTY_FIELD)
                        .param("password", Const.PWD)
                        .param("fullname", Const.FULLNAME)
                        .param("role", Const.ROLE_USER))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().hasErrors());

        User user = userRepository.findById(userId).get();

        assertEquals(Const.USERNAME, user.getUsername());
    }

    @Test
    void deleteUser_validId() throws Exception {
        populateDBWithUsers(10);
        int userId = getFirstValidId();

        mockMvc.perform(get("/user/delete/" + userId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        assertFalse(userRepository.existsById(userId));
        assertTrue(userRepository.findAll().size() == 9);
    }

    @Test
    void deleteUser_invalidId() throws Exception {
        populateDBWithUsers(10);
        int nonExistentId = -1;

        Exception exception = assertThrows(ServletException.class,() -> { //TODO:  A Revoir
            mockMvc.perform(get("/user/delete/" + nonExistentId));
        });

        String expectedMessage = "Invalid user Id: " + nonExistentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertTrue(userRepository.findAll().size() == 10);
    }


}
