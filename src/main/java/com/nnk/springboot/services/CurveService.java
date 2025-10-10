package com.nnk.springboot.services;

import com.nnk.springboot.models.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurveService implements ServiceCRUDAble<CurvePoint> {

    @Autowired
    private CurvePointRepository repository;

    @Override
    public List<CurvePoint> fetchAll() {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            log.error("couln't read the DB");
            return Collections.emptyList();
        }
    }

    @Override
    public void createAndSave(CurvePoint curvePoint) {
        try {
            repository.save(curvePoint);
        } catch (DataAccessException e) {
            log.error("Couldn't create the curvePoint {}", curvePoint.getTerm());
        }
    }

    @Override
    public Optional<CurvePoint> fetchById(Integer id) {
        Optional<CurvePoint> optCurvePoint = Optional.empty();
        try {
            optCurvePoint = repository.findById(id);
        } catch (DataAccessException e) {
            log.error("couldn't fetch the curvePoint with its id: {}", id);
        }
        return optCurvePoint;
    }

    @Override
    public void updateAndSave(CurvePoint curvePoint) {
        try {
            repository.save(curvePoint);
        } catch (DataAccessException e) {
            log.error("couldn't update the curvePoint {}", curvePoint.getTerm());
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
            log.error("couldn't delete de curvePoint with its id: {}", id);
        }
    }
}
