package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurveControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    //TODO : PENSER A TESTER LE VALIDATOR AUSSI

    private void populateDBWithCurvePoints(int curvePoints) {
        for (int i = 0; i < curvePoints; i++) {
            CurvePoint curvePoint = new CurvePoint();
            curvePoint.setCurveId((byte) (Const.CURVE_ID + (byte) i));
            curvePoint.setAsOfDate(Const.AS_OF_DATE);
            curvePoint.setTerm(Const.TERM + i);
            curvePoint.setValue(Const.VALUE + i);
            curvePoint.setCreationDate(Const.CREATION_DATE);

            repository.save(curvePoint);
        }
    }

    private void populateDBWithOneCurvePoint() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(Const.CURVE_ID);
        curvePoint.setAsOfDate(Const.AS_OF_DATE);
        curvePoint.setTerm(Const.TERM);
        curvePoint.setValue(Const.VALUE);
        curvePoint.setCreationDate(Const.CREATION_DATE);

        repository.save(curvePoint);
    }

    private int getFirstValidId() {
        List<CurvePoint> curvePointList = repository.findAll();
        if (curvePointList.size() == 0) {
            return -1;
        } else {
            return curvePointList.getFirst().getId();
        }
    }

    /*
     * TESTS BELOW FOR NOT LOGGED-IN USERS
     * SPRING SECURITY SHOULD REDIRECT TO HOME PAGE FOR LOGIN
     */

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void addCurvePointForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void validate() throws Exception {
        mockMvc.perform(post("/curvePoint/validate"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void showUpdateForm() throws Exception {
        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void updateCurvePoint() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteCurvePoint() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    /*
     * TESTS BELOW FOR LOGGED-IN USERS
     */

    @Test
    void whenValidInput_thenCreateCurvePoint() throws Exception {
        final int sizeOfList = repository.findAll().size();

        mockMvc.perform(post("/curvePoint/validate")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf())
                        .param("curveId", String.valueOf(Const.CURVE_ID))
                        .param("term", String.valueOf(Const.TERM))
                        .param("value", String.valueOf(Const.VALUE)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        List<CurvePoint> curvePointList = repository.findAll();

        CurvePoint curvePoint = curvePointList.getFirst();

        assertEquals(Const.TERM, curvePoint.getTerm());
        assertEquals(Const.VALUE, curvePoint.getValue());

        assertEquals(sizeOfList + 1, curvePointList.size());

        assertNotNull(curvePoint.getId());
    }

    @Test
    void whenInvalidInput_thenShowErrors() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf())
                        .param("curveId", String.valueOf(Const.CURVE_ID))
                        .param("term", Const.EMPTY_FIELD)
                        .param("value", String.valueOf(Const.VALUE)))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    void homeWithCurvePoints_shouldReturnPopulatedList() throws Exception {
        populateDBWithCurvePoints(100);

        MvcResult result = mockMvc.perform(get("/curvePoint/list")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andReturn();
        List<CurvePoint> curvePointList = (List<CurvePoint>) result.getModelAndView().getModel().get("curvePoints");
        assertEquals(100, curvePointList.size());
    }

    @Test
    void homeWithoutCurvePoints_shouldReturnEmptyList() throws Exception {
        MvcResult result = mockMvc.perform(get("/curvePoint/list")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andReturn();
        List<CurvePoint> curvePointList = (List<CurvePoint>) result.getModelAndView().getModel().get("curvePoints");
        assertEquals(0, curvePointList.size());
    }

    @Test
    void addCurvePointGetMapping() throws Exception {
        mockMvc.perform(get("/curvePoint/add")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    void showUpdateForm_valid_id() throws Exception {
        populateDBWithCurvePoints(1);
        int id = getFirstValidId();
        mockMvc.perform(get("/curvePoint/update/" + id)
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    void showUpdateForm_invalid_id() throws Exception {

        int nonExistentId = -1;

        Exception exception = assertThrows(ServletException.class,() -> {
            mockMvc.perform(get("/curvePoint/update/" + nonExistentId)
                    .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                    .with(csrf()));
        });

        String expectedMessage = "Invalid curvePoint Id: " + nonExistentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateCurvePoint_valid_CurvePoint() throws Exception {
        populateDBWithOneCurvePoint();
        int id = getFirstValidId();

        mockMvc.perform(post("/curvePoint/update/" + id)
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf())
                        .param("curveId", String.valueOf(Const.CURVE_ID))
                        .param("term", String.valueOf(Const.TERM_UPDATED))
                        .param("value", String.valueOf(Const.VALUE)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        CurvePoint curvePoint = repository.findById(id).get();

        assertEquals(Const.TERM_UPDATED, curvePoint.getTerm());
    }

    @Test
    void updateCurvePoint_invalid_CurvePoint() throws Exception {
        populateDBWithOneCurvePoint();
        int id = getFirstValidId();

        mockMvc.perform(post("/curvePoint/update/" + id)
                        .param("curveId", String.valueOf(Const.CURVE_ID))
                        .param("term", Const.EMPTY_FIELD)
                        .param("value", String.valueOf(Const.VALUE))
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().hasErrors());

        CurvePoint curvePoint = repository.findById(id).get();

        assertEquals(Const.TERM, curvePoint.getTerm());
    }

    @Test
    void deleteCurvePoint_validId() throws Exception {
        populateDBWithCurvePoints(10);
        int id = getFirstValidId();

        mockMvc.perform(get("/curvePoint/delete/" + id)
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        assertFalse(repository.existsById(id));
        assertTrue(repository.findAll().size() == 9);
    }

    @Test
    void deleteCurvePoint_invalidId() throws Exception {
        populateDBWithCurvePoints(10);
        int nonExistentId = -1;

        Exception exception = assertThrows(ServletException.class,() -> { //TODO:  A Revoir
            mockMvc.perform(get("/curvePoint/delete/" + nonExistentId)
                    .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                    .with(csrf()));
        });

        String expectedMessage = "Invalid curvePoint Id: " + nonExistentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertTrue(repository.findAll().size() == 10);
    }

}
