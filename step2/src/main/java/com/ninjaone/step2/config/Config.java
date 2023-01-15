package com.ninjaone.step2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class Config {

    @Bean
    ObjectMapper mapper() {
        return new ObjectMapper();
    }
}
