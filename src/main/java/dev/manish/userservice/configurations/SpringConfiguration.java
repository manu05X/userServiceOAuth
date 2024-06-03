package dev.manish.userservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringConfiguration {
    @Bean
     public BCryptPasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder(16);
     }
}

/**
 * We should create all the beans in configuration
 * */