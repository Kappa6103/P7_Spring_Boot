package com.nnk.springboot.services;

import com.nnk.springboot.models.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public List<User> fetchAllUsers() {
        try {
            return userRepository.findAll();
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't read the DB");
            return Collections.emptyList();
        }
    }

    public void createAndSave(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't create the user {}", user.getFullname());
        }
    }

    public Optional<User> fetchById(Integer id) {
        Optional<User> optUser = Optional.empty();
        try {
             optUser = userRepository.findById(id);
        } catch (DataAccessException dataAccessException) {
            log.error("couln't fetch the user with its id: {}", id);
        }
        return optUser;
    }

    public void updateAndSave(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't update the user {}", user.getFullname());
        }
    }

    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        try {
            userRepository.deleteById(id);
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't delete the user with its id: {}", id);
        }
    }
}
