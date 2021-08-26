package com.weather.api.locationconnectorservice.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

//TODO try this out with GSON
@Configuration
public class ObjectMapperConfig {

    @Bean
    @Primary
    public ObjectMapper locationObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.registerModule( new JavaTimeModule() );
        return objectMapper;
    }
}
