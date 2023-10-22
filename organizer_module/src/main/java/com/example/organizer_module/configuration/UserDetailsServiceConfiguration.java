package com.example.organizer_module.configuration;

import com.example.organizer_module.service.OrganizerService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@AllArgsConstructor
public class UserDetailsServiceConfiguration {

    private final OrganizerService organizerService;
    @Bean
    public UserDetailsService userDetailsService() {
        return organizerService::getOrganizerByEmail;
    }
}