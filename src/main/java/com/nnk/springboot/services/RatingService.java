package com.nnk.springboot.services;

import com.nnk.springboot.models.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.interfaces.ServiceCRUDAble;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RatingService implements ServiceCRUDAble<Rating> {

    @Autowired
    RatingRepository repository;

    @Override
    public List<Rating> fetchAll() {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            log.error("couldn't read the DB");
            return Collections.emptyList();
        }
    }

    @Override
    public void createAndSave(Rating rating) {
        try {
            repository.save(rating);
        } catch (DataAccessException e) {
            log.error("couldn't create the rating {}", rating.getId());
        }
    }

    @Override
    public Optional<Rating> fetchById(Integer id) {
        Optional<Rating> optionalRating = Optional.empty();
        try {
            optionalRating = repository.findById(id);
        } catch (DataAccessException e) {
            log.error("couldn't fetch the rating with its id: {}", id);
        }
        return optionalRating;
    }

    @Override
    public void updateAndSave(Rating rating) {
        try {
            repository.save(rating);
        } catch (DataAccessException e) {
            log.error("couldn't update the rating {}", rating.getId());
        }
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        try {
            repository.deleteById(id);
        } catch (DataAccessException e) {
            log.error("couldn't delete the rating with its id: {}", id);
        }
    }
}
