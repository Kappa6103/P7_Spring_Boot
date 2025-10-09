package com.nnk.springboot.services;

import com.nnk.springboot.models.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.interfaces.ServiceCRUDAble;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BidListService implements ServiceCRUDAble<BidList> {

    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public List<BidList> fetchAll() {
        try {
            return bidListRepository.findAll();
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't read the DB");
            return Collections.emptyList();
        }
    }

    @Override
    public void createAndSave(BidList bidList) {
        try {
            bidListRepository.save(bidList);
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't create the bidlist {}", bidList.getAccount());
        }
    }

    @Override
    public Optional<BidList> fetchById(Integer id) {
        Optional<BidList> optBidList = Optional.empty();
        try {
            optBidList = bidListRepository.findById(id);
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't fetch the bidlist with the id : {}",id);
        }
        return optBidList;
    }

    @Override
    public void updateAndSave(BidList bidList) {
        try {
            bidListRepository.save(bidList);
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't update the bidList {}", bidList.getAccount());
        }

    }

    @Override
    public boolean existsById(Integer id) {
        return bidListRepository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        try {
            bidListRepository.deleteById(id);
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't delete the user with its id: {}", id);
        }
    }
}
