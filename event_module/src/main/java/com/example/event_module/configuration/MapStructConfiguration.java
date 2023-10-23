package com.example.event_module.configuration;

import com.example.event_module.model.mapper.EventMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructConfiguration {
    @Bean
    public EventMapper eventMapper() {
        return EventMapper.INSTANCE;
    }


}
