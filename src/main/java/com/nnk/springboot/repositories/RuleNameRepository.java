package com.nnk.springboot.repositories;

import com.nnk.springboot.models.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
