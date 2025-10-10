package com.nnk.springboot.services;

import com.nnk.springboot.models.CurvePoint;
import com.nnk.springboot.services.interfaces.ServiceCRUDAble;

import java.util.List;
import java.util.Optional;

public class CurveService implements ServiceCRUDAble<CurvePoint> {
    @Override
    public List<CurvePoint> fetchAll() {
        return List.of();
    }

    @Override
    public void createAndSave(CurvePoint curvePoint) {

    }

    @Override
    public Optional<CurvePoint> fetchById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void updateAndSave(CurvePoint curvePoint) {

    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
