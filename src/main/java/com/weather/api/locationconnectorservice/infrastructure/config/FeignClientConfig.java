package com.weather.api.locationconnectorservice.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoLocationDatabaseFeignClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FeignClientConfig {

    private final String geolocationApiUrl;

    public FeignClientConfig(@Value("${geolocation-db.url}") final String geolocationApiUrl) {
        this.geolocationApiUrl = geolocationApiUrl;
    }

    @Bean
    public GeoLocationDatabaseFeignClient nationalRegistryFeignClient(@Qualifier("locationObjectMapper") final ObjectMapper objectMapper) {
        log.info("Creating LocationFeignClient");
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
//                .errorDecoder( new ExternalProcessingErrorDecoder() )
                .target(GeoLocationDatabaseFeignClient.class, geolocationApiUrl);
    }
}
