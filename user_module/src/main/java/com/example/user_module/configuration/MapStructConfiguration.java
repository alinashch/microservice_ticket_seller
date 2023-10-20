package com.example.user_module.configuration;

import com.example.user_module.mapper.RoleMapper;
import com.example.user_module.mapper.UserMapper;
import com.example.user_module.mapper.VerificationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructConfiguration {
    @Bean
    public RoleMapper roleMapper() {
        return RoleMapper.INSTANCE;
    }

    @Bean
    public UserMapper userMapper() {
        return UserMapper.INSTANCE;
    }

    @Bean
    public VerificationMapper verificationMapper() {
        return VerificationMapper.INSTANCE;
    }

}
