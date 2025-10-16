package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.Rating;
import com.nnk.springboot.models.User;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    private void populateDBWithRatings(int ratings) {
        for (int i = 0; i < ratings; i++) {
            Rating rating = new Rating();
            rating.setMoodysRating(Const.MOODYS_RATING + i);
            rating.setSandPRating(Const.S_AND_P_RATING + i);
            rating.setFitchRating(Const.FITCH_RATING + i);
            rating.setOrderNumber((byte) (Const.ORDER_NUMBER + i));

            repository.save(rating);
        }
    }

    private void populateDBWithOneRating() {
        Rating rating = new Rating();
        rating.setMoodysRating(Const.MOODYS_RATING);
        rating.setSandPRating(Const.S_AND_P_RATING);
        rating.setFitchRating(Const.FITCH_RATING);
        rating.setOrderNumber(Const.ORDER_NUMBER);

        repository.save(rating);
    }

    private int getFirstValidId() {
        List<Rating> ratingList = repository.findAll();
        if (ratingList.size() == 0) {
            return -1;
        } else {
            return ratingList.getFirst().getId();
        }
    }

    /*
     * TESTS BELOW FOR NOT LOGGED-IN USERS
     * SPRING SECURITY SHOULD REDIRECT TO HOME PAGE FOR LOGIN
     */

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void addRatingForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void validate() throws Exception {
        mockMvc.perform(post("/rating/validate"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void showUpdateForm() throws Exception {
        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void updateRating() throws Exception {
        mockMvc.perform(post("/rating/update/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteRating() throws Exception {
        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    /*
     * TESTS BELOW FOR LOGGED-IN USERS
     */

    @Test
    void whenValidInput_thenCreateRating() throws Exception {
        final int sizeOfList = repository.findAll().size();

        mockMvc.perform(post("/rating/validate")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf())
                        .param("moodysRating", Const.MOODYS_RATING)
                        .param("sandPRating", Const.S_AND_P_RATING)
                        .param("fitchRating", Const.FITCH_RATING)
                        .param("orderNumber", String.valueOf(Const.ORDER_NUMBER)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        List<Rating> ratingList = repository.findAll();
        Rating rating = ratingList.getFirst();

        assertEquals(Const.MOODYS_RATING, rating.getMoodysRating());
        assertEquals(Const.S_AND_P_RATING, rating.getSandPRating());
        assertEquals(Const.FITCH_RATING, rating.getFitchRating());
        assertEquals(Const.ORDER_NUMBER, rating.getOrderNumber());

        assertEquals(sizeOfList + 1, ratingList.size());

        assertNotNull(rating.getId());
    }

    @Test
    void whenInvalidInput_thenShowErrors() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf())
                        .param("moodysRating", Const.EMPTY_FIELD)
                        .param("sandPRating", Const.S_AND_P_RATING)
                        .param("fitchRating", Const.FITCH_RATING)
                        .param("orderNumber", String.valueOf(Const.ORDER_NUMBER)))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    void homeWithRatings_shouldReturnPopulatedList() throws Exception {
        populateDBWithRatings(100);

        MvcResult result = mockMvc.perform(get("/rating/list")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andReturn();
        List<Rating> ratingList = (List<Rating>) result.getModelAndView().getModel().get("ratings");
        assertEquals(100, ratingList.size());
    }

    @Test
    void homeWithoutRatings_shouldReturnEmptyList() throws Exception {
        MvcResult result = mockMvc.perform(get("/rating/list")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andReturn();
        List<Rating> ratingList = (List<Rating>) result.getModelAndView().getModel().get("ratings");
        assertEquals(0, ratingList.size());
    }

    @Test
    void addRatingGetMapping() throws Exception {
        mockMvc.perform(get("/rating/add")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("rating"));
    }

    @Test
    void showUpdateForm_valid_id() throws Exception {
        populateDBWithRatings(1);
        int id = getFirstValidId();
        mockMvc.perform(get("/rating/update/" + id)
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"));
    }


    @Test
    void showUpdateForm_invalid_id() throws Exception {

        int nonExistentId = -1;

        Exception exception = assertThrows(ServletException.class,() -> {
            mockMvc.perform(get("/rating/update/" + nonExistentId)
                    .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                    .with(csrf()));
        });

        String expectedMessage = "Invalid rating Id: " + nonExistentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateRating_valid_rating() throws Exception {
        populateDBWithOneRating();
        int id = getFirstValidId();

        mockMvc.perform(post("/rating/update/" + id)
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf())
                        .param("moodysRating", Const.MOODYS_RATING)
                        .param("sandPRating", Const.S_AND_P_RATING)
                        .param("fitchRating", Const.FITCH_RATING)
                        .param("orderNumber", String.valueOf(Const.ORDER_NUMBER_UPDATED)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        Rating rating = repository.findById(id).get();

        assertEquals(Const.ORDER_NUMBER_UPDATED, rating.getOrderNumber());
    }

    @Test
    void updateRating_invalid_rating() throws Exception {
        populateDBWithOneRating();
        int id = getFirstValidId();

        mockMvc.perform(post("/rating/update/" + id)
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf())
                        .param("moodysRating", Const.MOODYS_RATING)
                        .param("sandPRating", Const.S_AND_P_RATING)
                        .param("fitchRating", Const.FITCH_RATING)
                        .param("orderNumber", Const.EMPTY_FIELD))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().hasErrors());

        Rating rating = repository.findById(id).get();

        assertEquals(Const.ORDER_NUMBER, rating.getOrderNumber());
    }

    @Test
    void deleteUser_validId() throws Exception {
        populateDBWithRatings(10);
        int id = getFirstValidId();

        mockMvc.perform(get("/rating/delete/" + id)
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        assertFalse(repository.existsById(id));
        assertTrue(repository.findAll().size() == 9);
    }

    @Test
    void deleteUser_invalidId() throws Exception {
        populateDBWithRatings(10);
        int nonExistentId = -1;

        Exception exception = assertThrows(ServletException.class,() -> { //TODO:  A Revoir
            mockMvc.perform(get("/rating/delete/" + nonExistentId)
                    .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                    .with(csrf()));
        });

        String expectedMessage = "Invalid rating Id: " + nonExistentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertTrue(repository.findAll().size() == 10);
    }

}
