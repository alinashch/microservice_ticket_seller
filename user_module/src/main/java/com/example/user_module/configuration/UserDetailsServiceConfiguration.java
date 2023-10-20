package com.example.user_module.configuration;

import com.example.user_module.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@AllArgsConstructor
public class UserDetailsServiceConfiguration {

    private final UserService userService;
    @Bean
    public UserDetailsService userDetailsService() {
        return userService::getUserByEmail;
    }
}