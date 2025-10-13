package com.nnk.springboot.services;

import com.nnk.springboot.models.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleNameService implements ServiceCRUDAble<RuleName> {

    @Autowired
    RuleNameRepository repository;

    @Override
    public List<RuleName> fetchAll() {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            log.error("couldn't read the DB");
            return Collections.emptyList();
        }
    }

    @Override
    public void createAndSave(RuleName ruleName) {
        try {
            repository.save(ruleName);
        } catch (DataAccessException e) {
            log.error("couldn't create the ruleName {}", ruleName.getName());
        }
    }

    @Override
    public Optional<RuleName> fetchById(Integer id) {
        Optional<RuleName> optRuleName = Optional.empty();
        try {
            optRuleName = repository.findById(id);
        } catch (DataAccessException e) {
            log.error("couldn't fetch the ruleName with its id: {}", id);
        }
        return optRuleName;
    }

    @Override
    public void updateAndSave(RuleName ruleName) {
        try {
            repository.save(ruleName);
        } catch (DataAccessException e) {
            log.error("couldn't update the ruleName {}", ruleName.getName());
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
            log.error("couldn't delete the ruleName with its id: {}", id);
        }
    }
}
