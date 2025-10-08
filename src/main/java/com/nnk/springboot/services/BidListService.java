package com.nnk.springboot.services;

import com.nnk.springboot.models.BidList;
import com.nnk.springboot.services.interfaces.ServiceCRUDAble;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService implements ServiceCRUDAble<BidList> {
    @Override
    public List<BidList> fetchAll() {
        return List.of();
    }

    @Override
    public void createAndSave(BidList bidList) {

    }

    @Override
    public Optional<BidList> fetchById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void updateAndSave(BidList bidList) {

    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
