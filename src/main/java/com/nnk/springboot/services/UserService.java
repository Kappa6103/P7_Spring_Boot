package com.nnk.springboot.services;

import com.nnk.springboot.models.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.interfaces.ServiceCRUDAble;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * UserService is a service layer that provides business logic for managing User entities.
 * It implements the ServiceCRUDAble interface for CRUD operations and UserDetailsService
 * for integration with Spring Security's authentication provider.
 *
 * Responsibilities include:
 * - Fetching all User entities or a specific User by ID.
 * - Creating and saving new User entities.
 * - Updating existing User entities.
 * - Checking the existence of a User by ID.
 * - Deleting a User by ID.
 * - Loading UserDetails required for Spring Security authentication.
 *
 * Error handling is incorporated for DataAccessException to handle database-related issues
 * gracefully, and logging is used to log actions and errors.
 */
@Service
@Slf4j
public class UserService implements ServiceCRUDAble<User>, UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> fetchAll() {
        try {
            return userRepository.findAll();
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't read the DB");
            return Collections.emptyList();
        }
    }

    @Override
    public void createAndSave(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't create the user {}", user.getFullname());
        }
    }

    @Override
    public Optional<User> fetchById(Integer id) {
        Optional<User> optUser = Optional.empty();
        try {
             optUser = userRepository.findById(id);
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't fetch the user with its id : {}", id);
        }
        return optUser;
    }

    @Override
    public void updateAndSave(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't update the user {}", user.getFullname());
        }
    }

    @Override
    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        try {
            userRepository.deleteById(id);
        } catch (DataAccessException dataAccessException) {
            log.error("couldn't delete the user with its id: {}", id);
        }
    }

    /**
     * Loads a user by their username for authentication purposes.
     *
     * @param username the username of the user that needs to be loaded
     * @return UserDetails containing user-specific information used for authentication
     * @throws UsernameNotFoundException if the username is not found or is invalid
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isBlank()) {
            throw new UsernameNotFoundException("Username cannot be empty");
        }
        Optional<User> optionalUser = Optional.empty();
        try {
            optionalUser = userRepository.findByUsername(username);
        } catch (DataAccessException e) {
            log.error("couldn't fetch user {} for authentication", username);
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().toString())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
