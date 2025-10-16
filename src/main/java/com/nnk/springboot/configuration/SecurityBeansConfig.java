package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This Config class exists to avoid circular dependency loop in the main config class
 *
 * Please see the {@link com.nnk.springboot.configuration.SpringSecurityConfig} for the main config class
 * @author Kevin S
 *
 */
@Configuration
public class SecurityBeansConfig {

    /**
     * Defines a bean that provides a PasswordEncoder implementation using BCrypt hashing algorithm.
     * The encoder is used for encoding and verifying passwords in authentication and authorization processes.
     *
     * @return an instance of {@link BCryptPasswordEncoder}, which implements the {@link PasswordEncoder} interface.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
