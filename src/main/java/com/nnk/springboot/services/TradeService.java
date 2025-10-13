package com.nnk.springboot.services;

import com.nnk.springboot.models.Trade;
import com.nnk.springboot.services.interfaces.ServiceCRUDAble;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TradeService implements ServiceCRUDAble<Trade> {
    @Override
    public List<Trade> fetchAll() {
        return List.of();
    }

    @Override
    public void createAndSave(Trade trade) {

    }

    @Override
    public Optional<Trade> fetchById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void updateAndSave(Trade trade) {

    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
