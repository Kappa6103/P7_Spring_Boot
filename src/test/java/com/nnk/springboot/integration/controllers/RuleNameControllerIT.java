package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.BidList;
import com.nnk.springboot.models.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }


    private void populateDBWithRuleNames(int bidLists) {
        for (int i = 0; i < bidLists; i++) {
            RuleName ruleName = new RuleName();

            ruleName.setName(Const.NAME + i);
            ruleName.setDescription(Const.DESCRIPTION + i);
            ruleName.setJson(Const.JSON + i);
            ruleName.setTemplate(Const.TEMPLATE + i);
            ruleName.setSqlStr(Const.SQL_STR + i);
            ruleName.setSqlPart(Const.SQL_PART + i);

            repository.save(ruleName);
        }
    }

    private void populateDBWithOneRuleName() {
        RuleName ruleName = new RuleName();

        ruleName.setName(Const.NAME);
        ruleName.setDescription(Const.DESCRIPTION);
        ruleName.setJson(Const.JSON);
        ruleName.setTemplate(Const.TEMPLATE);
        ruleName.setSqlStr(Const.SQL_STR);
        ruleName.setSqlPart(Const.SQL_PART);

        repository.save(ruleName);
    }

    private int getFirstValidId() {
        List<RuleName> ruleNames = repository.findAll();
        if (ruleNames.size() == 0) {
            return -1;
        } else {
            return ruleNames.getFirst().getId();
        }
    }

    @Test
    void whenValidInput_thenCreateRuleName() throws Exception {
        final int sizeOfList = repository.findAll().size();

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", Const.NAME)
                        .param("description", Const.DESCRIPTION)
                        .param("json", Const.JSON)
                        .param("template", Const.TEMPLATE)
                        .param("sqlStr", Const.SQL_STR)
                        .param("sqlPart", Const.SQL_PART))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        List<RuleName> ruleNameList = repository.findAll();
        RuleName ruleName = ruleNameList.getFirst();

        assertEquals(Const.NAME, ruleName.getName());
        assertEquals(Const.DESCRIPTION, ruleName.getDescription());
        assertEquals(Const.JSON, ruleName.getJson());
        assertEquals(Const.TEMPLATE, ruleName.getTemplate());
        assertEquals(Const.SQL_STR, ruleName.getSqlStr());
        assertEquals(Const.SQL_PART, ruleName.getSqlPart());

        assertEquals(sizeOfList + 1, ruleNameList.size());

        assertNotNull(ruleName.getId());
    }

    @Test
    void whenInvalidInput_thenShowErrors() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", Const.EMPTY_FIELD)
                        .param("description", Const.DESCRIPTION)
                        .param("json", Const.JSON)
                        .param("template", Const.TEMPLATE)
                        .param("sqlStr", Const.SQL_STR)
                        .param("sqlPart", Const.SQL_PART))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    void homeWithRuleNames_shouldReturnPopulatedList() throws Exception {
        populateDBWithRuleNames(100);

        MvcResult result = mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andReturn();
        List<RuleName> ruleNameList = (List<RuleName>) result.getModelAndView().getModel().get("ruleNames");
        assertEquals(100, ruleNameList.size());
    }

    @Test
    void homeWithoutRuleNames_shouldReturnEmptyList() throws Exception {
        MvcResult result = mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andReturn();
        List<RuleName> ruleNameList = (List<RuleName>) result.getModelAndView().getModel().get("ruleNames");
        assertEquals(0, ruleNameList.size());
    }

    @Test
    void addRuleNameGetMapping() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    void showUpdateForm_valid_id() throws Exception {
        populateDBWithRuleNames(1);
        int id = getFirstValidId();
        mockMvc.perform(get("/ruleName/update/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    void showUpdateForm_invalid_id() throws Exception {

        int nonExistentId = -1;

        Exception exception = assertThrows(ServletException.class,() -> {
            mockMvc.perform(get("/ruleName/update/" + nonExistentId));
        });

        String expectedMessage = "Invalid ruleName Id: " + nonExistentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateRuleName_valid_ruleName() throws Exception {
        populateDBWithOneRuleName();
        int id = getFirstValidId();

        mockMvc.perform(post("/ruleName/update/" + id)
                        .param("name", Const.NAME_UPDATED)
                        .param("description", Const.DESCRIPTION)
                        .param("json", Const.JSON)
                        .param("template", Const.TEMPLATE)
                        .param("sqlStr", Const.SQL_STR)
                        .param("sqlPart", Const.SQL_PART))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        RuleName ruleName = repository.findById(id).get();

        assertEquals(Const.NAME_UPDATED, ruleName.getName());
    }

    @Test
    void updateRuleName_invalid_ruleName() throws Exception {
        populateDBWithOneRuleName();
        int id = getFirstValidId();

        mockMvc.perform(post("/ruleName/update/" + id)
                        .param("name", Const.EMPTY_FIELD)
                        .param("description", Const.DESCRIPTION)
                        .param("json", Const.JSON)
                        .param("template", Const.TEMPLATE)
                        .param("sqlStr", Const.SQL_STR)
                        .param("sqlPart", Const.SQL_PART))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().hasErrors());

        RuleName ruleName = repository.findById(id).get();

        assertEquals(Const.NAME, ruleName.getName());
    }

    @Test
    void deleteRuleName_validId() throws Exception {
        populateDBWithRuleNames(10);
        int id = getFirstValidId();

        mockMvc.perform(get("/ruleName/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        assertFalse(repository.existsById(id));
        assertTrue(repository.findAll().size() == 9);
    }

    @Test
    void deleteRuleName_invalidId() throws Exception {
        populateDBWithRuleNames(10);
        int nonExistentId = -1;

        Exception exception = assertThrows(ServletException.class,() -> { //TODO:  A Revoir
            mockMvc.perform(get("/ruleName/delete/" + nonExistentId));
        });

        String expectedMessage = "Invalid ruleName Id: " + nonExistentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertTrue(repository.findAll().size() == 10);
    }


}
