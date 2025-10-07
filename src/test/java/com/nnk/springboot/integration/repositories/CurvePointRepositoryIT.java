package com.nnk.springboot.integration.repositories;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class CurvePointRepositoryIT {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@BeforeEach
	public void init() {
		curvePointRepository.deleteAll();
	}

	@Test
	public void curvePointTest() {
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setCurveId(Const.CURVE_ID);
		curvePoint.setTerm(Const.TERM);
		curvePoint.setValue(Const.VALUE);

		// Save
		curvePoint = curvePointRepository.save(curvePoint);
		assertNotNull(curvePoint.getId());
		assertEquals(Const.CURVE_ID, curvePoint.getCurveId());
		assertEquals(Const.TERM, curvePoint.getTerm());
		assertEquals(Const.VALUE, curvePoint.getValue());


		// Update
		curvePoint.setCurveId(Const.CURVE_ID_UPDATED);
		curvePoint = curvePointRepository.save(curvePoint);
		assertEquals(Const.CURVE_ID_UPDATED, curvePoint.getCurveId());

		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
		assertEquals(1, listResult.size());

		// Delete
		Integer id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		assertFalse(curvePointList.isPresent());
	}

}
