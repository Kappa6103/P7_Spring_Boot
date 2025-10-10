package com.nnk.springboot.services;

import com.nnk.springboot.models.Rating;
import com.nnk.springboot.services.interfaces.ServiceCRUDAble;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RatingService implements ServiceCRUDAble<Rating> {
    @Override
    public List<Rating> fetchAll() {
        return List.of();
    }

    @Override
    public void createAndSave(Rating rating) {

    }

    @Override
    public Optional<Rating> fetchById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void updateAndSave(Rating rating) {

    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
