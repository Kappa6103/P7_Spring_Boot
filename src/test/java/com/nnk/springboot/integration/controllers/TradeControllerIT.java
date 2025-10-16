package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.BidList;
import com.nnk.springboot.models.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    private void populateDBWithTrades(int trades) {
        for (int i = 0; i < trades; i++) {
            Trade trade = new Trade();

            trade.setAccount(Const.ACCOUNT + i);
            trade.setType(Const.TYPE + i);
            trade.setBuyQuantity(Const.BUY_QUANTITY + i);

            repository.save(trade);
        }
    }

    private void populateDBWithOneTrade() {
        Trade trade = new Trade();

        trade.setAccount(Const.ACCOUNT);
        trade.setType(Const.TYPE);
        trade.setBuyQuantity(Const.BUY_QUANTITY);

        repository.save(trade);
    }

    private int getFirstValidId() {
        List<Trade> tradeList = repository.findAll();
        if (tradeList.size() == 0) {
            return -1;
        } else {
            return tradeList.getFirst().getId();
        }
    }

    @Test
    void whenValidInput_thenCreateTrade() throws Exception {
        final int sizeOfList = repository.findAll().size();

        mockMvc.perform(post("/trade/validate")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf())
                        .param("account", Const.ACCOUNT)
                        .param("type", Const.TYPE)
                        .param("buyQuantity", String.valueOf(Const.BUY_QUANTITY)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        List<Trade> tradeList = repository.findAll();
        Trade trade = tradeList.getFirst();

        assertEquals(Const.ACCOUNT,trade.getAccount());
        assertEquals(Const.TYPE, trade.getType());
        assertEquals(Const.BUY_QUANTITY, trade.getBuyQuantity());

        assertEquals(sizeOfList + 1, tradeList.size());

        assertNotNull(trade.getId());
    }

    @Test
    void whenInvalidInput_thenShowErrors() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf())
                        .param("account", Const.EMPTY_FIELD)
                        .param("type", Const.TYPE)
                        .param("buyQuantity", String.valueOf(Const.BUY_QUANTITY)))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    void homeWithTrades_shouldReturnPopulatedList() throws Exception {
        populateDBWithTrades(100);

        MvcResult result = mockMvc.perform(get("/trade/list")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andReturn();
        List<Trade> tradeList = (List<Trade>) result.getModelAndView().getModel().get("trades");
        assertEquals(100, tradeList.size());
    }

    @Test
    void homeWithoutTrades_shouldReturnEmptyList() throws Exception {
        MvcResult result = mockMvc.perform(get("/trade/list")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andReturn();
        List<Trade> tradeList = (List<Trade>) result.getModelAndView().getModel().get("trades");
        assertEquals(0, tradeList.size());
    }

    @Test
    void addTradeGetMapping() throws Exception {
        mockMvc.perform(get("/trade/add")
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    void showUpdateForm_valid_id() throws Exception {
        populateDBWithTrades(1);
        int id = getFirstValidId();
        mockMvc.perform(get("/trade/update/" + id)
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    void showUpdateForm_invalid_id() throws Exception {

        int nonExistentId = -1;

        Exception exception = assertThrows(ServletException.class,() -> {
            mockMvc.perform(get("/trade/update/" + nonExistentId)
                    .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                    .with(csrf()));
        });

        String expectedMessage = "Invalid trade Id: " + nonExistentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateTrade_valid_trade() throws Exception {
        populateDBWithOneTrade();
        int id = getFirstValidId();

        mockMvc.perform(post("/trade/update/" + id)
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf())
                        .param("account", Const.ACCOUNT_UPDATED)
                        .param("type", Const.TYPE)
                        .param("buyQuantity", String.valueOf(Const.BUY_QUANTITY)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        Trade trade = repository.findById(id).get();

        assertEquals(Const.ACCOUNT_UPDATED, trade.getAccount());
    }

    @Test
    void updateTrade_invalid_trade() throws Exception {
        populateDBWithOneTrade();
        int id = getFirstValidId();

        mockMvc.perform(post("/trade/update/" + id)
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf())
                        .param("account", Const.EMPTY_FIELD)
                        .param("type", Const.TYPE)
                        .param("bidQuantity", String.valueOf(Const.BID_QUANTITY)))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().hasErrors());

        Trade trade = repository.findById(id).get();

        assertEquals(Const.ACCOUNT, trade.getAccount());
    }

    @Test
    void deleteTrade_validId() throws Exception {
        populateDBWithTrades(10);
        int id = getFirstValidId();

        mockMvc.perform(get("/trade/delete/" + id)
                        .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        assertFalse(repository.existsById(id));
        assertTrue(repository.findAll().size() == 9);
    }

    @Test
    void deleteBidList_invalidId() throws Exception {
        populateDBWithTrades(10);
        int nonExistentId = -1;

        Exception exception = assertThrows(ServletException.class,() -> {
            mockMvc.perform(get("/trade/delete/" + nonExistentId)
                    .with(user(Const.AUTH_USERNAME).password(Const.AUTH_PWD).roles(Const.AUTH_ROLE_USER))
                    .with(csrf()));
        });

        String expectedMessage = "Invalid trade Id: " + nonExistentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertTrue(repository.findAll().size() == 10);
    }

}
