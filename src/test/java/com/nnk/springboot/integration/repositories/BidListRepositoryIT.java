package com.nnk.springboot.integration.repositories;

import com.nnk.springboot.configuration.SpringSecurityConfig;
import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@SpringBootTest
public class BidListRepositoryIT {

	@Autowired
	private BidListRepository bidListRepository;

	@BeforeEach
	public void init() {
		bidListRepository.deleteAll();
	}

	@Test
	public void bidListTest() {
		BidList bid = new BidList(Const.ACCOUNT, Const.TYPE);
		bid.setBidQuantity(Const.BID_QUANTITY);

		// Save
		bid = bidListRepository.save(bid);
		assertNotNull(bid.getId());
		assertEquals(Const.ACCOUNT, bid.getAccount());
		assertEquals(Const.TYPE, bid.getType());

		assertEquals(bid.getBidQuantity(), Const.BID_QUANTITY, 10d); //TODO adjust delta, add to const

		// Update
		bid.setBidQuantity(Const.BID_QUANTITY_UPDATED);
		bid = bidListRepository.save(bid);
		assertEquals(bid.getBidQuantity(), Const.BID_QUANTITY_UPDATED, 20d); //TODO adjust delta, add to const

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		assertEquals(1, listResult.size());

		// Delete
		Integer id = bid.getId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		assertFalse(bidList.isPresent());
	}
}
