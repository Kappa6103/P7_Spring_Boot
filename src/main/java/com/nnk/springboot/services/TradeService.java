package com.nnk.springboot.services;

import com.nnk.springboot.models.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeService implements ServiceCRUDAble<Trade> {

    @Autowired
    private TradeRepository repository;

    @Override
    public List<Trade> fetchAll() {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            log.error("couldn't read the DB");
            return Collections.emptyList();
        }
    }

    @Override
    public void createAndSave(Trade trade) {
        try {
            repository.save(trade);
        } catch (DataAccessException e) {
            log.error("couldn't create the trade Id :{}", trade.getId());
        }
    }

    @Override
    public Optional<Trade> fetchById(Integer id) {
        Optional<Trade> optTrade = Optional.empty();
        try {
            optTrade = repository.findById(id);
        } catch (DataAccessException e) {
            log.error("couldn't fetch the trade with its id: {}", id);
        }
        return optTrade;
    }

    @Override
    public void updateAndSave(Trade trade) {
        try {
            repository.save(trade);
        } catch (DataAccessException e) {
            log.error("couldn't update the trade", trade.getId());
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
            log.error("couldn't delete the trade with its id: {}", id);
        }
    }
}
