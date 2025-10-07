package com.nnk.springboot.integration.repositories;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class RuleNameRepositoryIT {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@BeforeEach
	public void init() {
		ruleNameRepository.deleteAll();
	}

	@Test
	public void ruleTest() {
		RuleName rule = new RuleName();
		rule.setName(Const.NAME);
		rule.setDescription(Const.DESCRIPTION);
		rule.setJson(Const.JSON);
		rule.setTemplate(Const.TEMPLATE);
		rule.setSqlStr(Const.SQL_STR);
		rule.setSqlPart(Const.SQL_PART);

		// Save
		rule = ruleNameRepository.save(rule);
		assertNotNull(rule.getId());
		assertEquals(Const.NAME, rule.getName());
		assertEquals(Const.DESCRIPTION, rule.getDescription());
		assertEquals(Const.JSON, rule.getJson());
		assertEquals(Const.TEMPLATE, rule.getTemplate());
		assertEquals(Const.SQL_STR, rule.getSqlStr());
		assertEquals(Const.SQL_PART, rule.getSqlPart());

		// Update
		rule.setName(Const.NAME_UPDATED);
		rule = ruleNameRepository.save(rule);
		assertEquals(Const.NAME_UPDATED, rule.getName());

		// Find
		List<RuleName> listResult = ruleNameRepository.findAll();
		assertEquals(1,listResult.size());

		// Delete
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		assertFalse(ruleList.isPresent());
	}
}
