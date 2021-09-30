package com.weather.api.locationconnectorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties
public class LocationConnectorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationConnectorServiceApplication.class, args);
    }

}
