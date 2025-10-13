package com.nnk.springboot.integration.repositories;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class TradeRepositoryIT {

	@Autowired
	private TradeRepository tradeRepository;

	@BeforeEach
	public void init() {
		tradeRepository.deleteAll();
	}

	@Test
	public void tradeTest() {
		Trade trade = new Trade(Const.ACCOUNT, Const.TYPE, Const.BUY_QUANTITY);

		// Save
		trade = tradeRepository.save(trade);
		assertNotNull(trade.getId());
		assertEquals(Const.ACCOUNT, trade.getAccount());
		assertEquals(Const.TYPE, trade.getType());
		assertEquals(Const.BUY_QUANTITY, trade.getBuyQuantity());

		// Update
		trade.setAccount(Const.ACCOUNT_UPDATED);
		trade = tradeRepository.save(trade);
		assertEquals(Const.ACCOUNT_UPDATED, trade.getAccount());

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		assertEquals(1, listResult.size());

		// Delete
		Integer id = trade.getId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		assertFalse(tradeList.isPresent());
	}
}
