package com.nnk.springboot.services;

import com.nnk.springboot.models.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.interfaces.ServiceCRUDAble;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RuleNameService implements ServiceCRUDAble<RuleName> {

    @Autowired
    RuleNameRepository repository;

    @Override
    public List<RuleName> fetchAll() {
        return List.of();
    }

    @Override
    public void createAndSave(RuleName ruleName) {

    }

    @Override
    public Optional<RuleName> fetchById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void updateAndSave(RuleName ruleName) {

    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
