package com.nnk.springboot.repositories;

import com.nnk.springboot.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AppUserRepository extends JpaRepository<AppUser, Integer>, JpaSpecificationExecutor<AppUser> {

}
