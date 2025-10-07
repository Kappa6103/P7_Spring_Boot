package com.nnk.springboot.integration.repositories;

import com.nnk.springboot.constant.Const;
import com.nnk.springboot.models.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class RatingRepositoryIT {

	@Autowired
	private RatingRepository ratingRepository;

	@BeforeEach
	public void init() {
		ratingRepository.deleteAll();
	}

	@Test
	public void ratingTest() {
		Rating rating = new Rating();
		rating.setMoodysRating(Const.MOODYS_RATING);
		rating.setSandPRating(Const.S_AND_P_RATING);
		rating.setFitchRating(Const.FITCH_RATING);
		rating.setOrderNumber(Const.ORDER_NUMBER);

		// Save
		rating = ratingRepository.save(rating);
		assertNotNull(rating.getId());
		assertEquals(Const.MOODYS_RATING, rating.getMoodysRating());
		assertEquals(Const.S_AND_P_RATING, rating.getSandPRating());
		assertEquals(Const.FITCH_RATING, rating.getFitchRating());
		assertEquals(Const.ORDER_NUMBER, rating.getOrderNumber());

		// Update
		rating.setOrderNumber(Const.ORDER_NUMBER_UPDATED);
		rating = ratingRepository.save(rating);
		assertEquals(Const.ORDER_NUMBER_UPDATED, rating.getOrderNumber());

		// Find
		List<Rating> listResult = ratingRepository.findAll();
		assertEquals(1, listResult.size());

		// Delete
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		assertFalse(ratingList.isPresent());
	}
}
