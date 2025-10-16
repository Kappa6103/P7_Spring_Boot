package com.nnk.springboot.configuration;

import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for setting up Spring Security.
 * It defines the security filter chain, authentication provider, and other security-related configurations.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Configures and sets up the security filter chain for the application.
     * This method defines the security policies such as authentication, authorization,
     * login and logout configurations, exception handling, and CSRF support.
     * It uses a custom authentication provider to integrate with user-specific
     * authentication logic and applies authorization rules for protected and public endpoints.
     *
     * @param http the {@link HttpSecurity} object used to configure the security settings
     *             for the application, including authentication, authorization, and other
     *             security-related features.
     * @return the configured {@link SecurityFilterChain} object to be used by the Spring
     *         Security framework for handling HTTP requests security.
     * @throws Exception if any error occurs during configuration of the security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/user/list", true)
                        .failureUrl("/login?error")
                )
                .logout(config -> config
                        .logoutSuccessUrl("/login?logout")
                )
                .exceptionHandling(config -> config
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/home"))
                )
                .csrf(Customizer.withDefaults()
                )
                .build();
    }

    /**
     * Creates and configures a DaoAuthenticationProvider bean.
     * This authentication provider is responsible for handling authentication
     * using a custom UserDetailsService and password encoder. It integrates
     * with the UserService for retrieving user details and with the PasswordEncoder
     * to encode and validate passwords securely.
     *
     * @return an instance of {@link DaoAuthenticationProvider} configured with
     * the custom user service and password encoder.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider(userService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }
}