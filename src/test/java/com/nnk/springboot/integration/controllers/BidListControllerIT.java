package com.nnk.springboot.integration.controllers;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidListControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    @BeforeEach
    void setUp() {
        bidListRepository.deleteAll();
    }

    //TODO : PENSER A TESTER LE VALIDATOR AUSSI

    private void populateDBWithBidList(int bidLists) {
        for (int i = 0; i < bidLists; i++) {
            BidList bidList = new BidList();
            bidList.setAccount(Const.ACCOUNT + i);
            bidList.setType(Const.TYPE + i);
            bidList.setBidQuantity(Const.BID_QUANTITY + i);
            bidList.setAskQuantity(Const.ASKQUANTITY + i);
            bidList.setBid(Const.BID + i);
            bidList.setAsk(Const.ASK + i);
            bidList.setBenchmark(Const.BENCHMARK + i);
            bidList.setBidListDate(Const.BIDLISTDATE);
            bidList.setCommentary(Const.COMMENTARY + i);
            bidList.setSecurity(Const.SECURITY + i);
            bidList.setStatus(Const.STATUS + i);
            bidList.setTrader(Const.TRADER + i);
            bidList.setBook(Const.BOOK + i);
            bidList.setCreationName(Const.CREATION_NAME + i);
            bidList.setCreationDate(Const.CREATION_DATE);
            bidList.setRevisionName(Const.REVISION_NAME + i);
            bidList.setRevisionDate(Const.REVISION_DATE);
            bidList.setDealName(Const.DEAL_NAME + i);
            bidList.setDealType(Const.DEAL_TYPE + i);
            bidList.setSourceListId(Const.SOURCELIST_ID + i);
            bidList.setSide(Const.SIDE + i);

            bidListRepository.save(bidList);
        }
    }

    private void populateDBWithOneBidList() {
        BidList bidList = new BidList();
        bidList.setAccount(Const.ACCOUNT);
        bidList.setType(Const.TYPE);
        bidList.setBidQuantity(Const.BID_QUANTITY);
        bidList.setAskQuantity(Const.ASKQUANTITY);
        bidList.setBid(Const.BID);
        bidList.setAsk(Const.ASK);
        bidList.setBenchmark(Const.BENCHMARK);
        bidList.setBidListDate(Const.BIDLISTDATE);
        bidList.setCommentary(Const.COMMENTARY);
        bidList.setSecurity(Const.SECURITY);
        bidList.setStatus(Const.STATUS);
        bidList.setTrader(Const.TRADER);
        bidList.setBook(Const.BOOK);
        bidList.setCreationName(Const.CREATION_NAME);
        bidList.setCreationDate(Const.CREATION_DATE);
        bidList.setRevisionName(Const.REVISION_NAME);
        bidList.setRevisionDate(Const.REVISION_DATE);
        bidList.setDealName(Const.DEAL_NAME);
        bidList.setDealType(Const.DEAL_TYPE);
        bidList.setSourceListId(Const.SOURCELIST_ID);
        bidList.setSide(Const.SIDE);

        bidListRepository.save(bidList);
    }

    private int getFirstValidId() {
        List<BidList> bidLists = bidListRepository.findAll();
        if (bidLists.size() == 0) {
            return -1;
        } else {
            return bidLists.getFirst().getId();
        }
    }

    @Test
    void whenValidInput_thenCreateBidList() throws Exception {
        final int sizeOfList = bidListRepository.findAll().size();

        mockMvc.perform(post("/bidList/validate")
                        .param("account", Const.ACCOUNT)
                        .param("type", Const.TYPE)
                        .param("bidQuantity", String.valueOf(Const.BID_QUANTITY)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        List<BidList> listBidList = bidListRepository.findAll();
        BidList bidList = listBidList.getFirst();

        assertEquals(Const.ACCOUNT,bidList.getAccount());
        assertEquals(Const.TYPE, bidList.getType());
        assertEquals(Const.BID_QUANTITY, bidList.getBidQuantity());

        assertEquals(sizeOfList + 1, listBidList.size());

        assertNotNull(bidList.getId());
    }

    @Test
    void whenInvalidInput_thenShowErrors() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", Const.EMPTY_FIELD)
                        .param("type", Const.TYPE)
                        .param("bidQuantity", String.valueOf(Const.BID_QUANTITY)))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    void homeWithBidLists_shouldReturnPopulatedList() throws Exception {
        populateDBWithBidList(100);

        MvcResult result = mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andReturn();
        List<BidList> listBidList = (List<BidList>) result.getModelAndView().getModel().get("bidLists");
        assertEquals(100, listBidList.size());
    }

    @Test
    void homeWithoutBidLists_shouldReturnEmptyList() throws Exception {
        MvcResult result = mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andReturn();
        List<BidList> listBidList = (List<BidList>) result.getModelAndView().getModel().get("bidLists");
        assertEquals(0, listBidList.size());
    }

    @Test
    void addBidListGetMapping() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    void showUpdateForm_valid_id() throws Exception {
        populateDBWithBidList(1);
        int id = getFirstValidId();
        mockMvc.perform(get("/bidList/update/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    void showUpdateForm_invalid_id() throws Exception {

        int nonExistentId = -1;

        Exception exception = assertThrows(ServletException.class,() -> {
            mockMvc.perform(get("/bidList/update/" + nonExistentId));
        });

        String expectedMessage = "Invalid bidList Id: " + nonExistentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateBidList_valid_BidList() throws Exception {
        populateDBWithOneBidList();
        int id = getFirstValidId();

        mockMvc.perform(post("/bidList/update/" + id)
                        .param("account", Const.ACCOUNT_UPDATED)
                        .param("type", Const.TYPE)
                        .param("bidQuantity", String.valueOf(Const.BID_QUANTITY)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        BidList bidList = bidListRepository.findById(id).get();

        assertEquals(Const.ACCOUNT_UPDATED, bidList.getAccount());
    }

    @Test
    void updateBidList_invalid_bidList() throws Exception {
        populateDBWithOneBidList();
        int id = getFirstValidId();

        mockMvc.perform(post("/bidList/update/" + id)
                        .param("account", Const.EMPTY_FIELD)
                        .param("type", Const.TYPE)
                        .param("bidQuantity", String.valueOf(Const.BID_QUANTITY)))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().hasErrors());

        BidList bidList = bidListRepository.findById(id).get();

        assertEquals(Const.ACCOUNT, bidList.getAccount());
    }

    @Test
    void deleteBidList_validId() throws Exception {
        populateDBWithBidList(10);
        int id = getFirstValidId();

        mockMvc.perform(get("/bidList/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        assertFalse(bidListRepository.existsById(id));
        assertTrue(bidListRepository.findAll().size() == 9);
    }

    @Test
    void deleteBidList_invalidId() throws Exception {
        populateDBWithBidList(10);
        int nonExistentId = -1;

        Exception exception = assertThrows(ServletException.class,() -> { //TODO:  A Revoir
            mockMvc.perform(get("/bidList/delete/" + nonExistentId));
        });

        String expectedMessage = "Invalid bidList Id: " + nonExistentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertTrue(bidListRepository.findAll().size() == 10);
    }


}
