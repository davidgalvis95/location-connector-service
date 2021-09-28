package com.weather.api.locationconnectorservice.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoDBOpenAPIFeignClient;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoLocationDatabaseFeignClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
public class FeignClientConfig {

    @Bean("geolocationDecoder")
    public GeolocationDatabaseFeignErrorDecoder geolocationDatabaseFeignErrorDecoder() {
        return new GeolocationDatabaseFeignErrorDecoder();
    }

    @Bean
    @Profile("dev")
    public GeoLocationDatabaseFeignClient devGeoLocationDatabaseFeignClient(@Qualifier("locationObjectMapper") final ObjectMapper objectMapper,
                                                                            @Qualifier("geolocationDecoder") GeolocationDatabaseFeignErrorDecoder geolocationDatabaseFeignErrorDecoder,
                                                                            @Qualifier("devGeoDbApiContext") GeolocationApiConfig.GeolocationContext openApiContext) {
        log.info("Creating GeoLocationDatabaseFeignClient: PROD");
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(geolocationDatabaseFeignErrorDecoder)
                .target(GeoLocationDatabaseFeignClient.class, openApiContext.getUrl());
    }

    @Bean
    @Profile("prod")
    public GeoLocationDatabaseFeignClient geoLocationDatabaseFeignClient(@Qualifier("locationObjectMapper") final ObjectMapper objectMapper,
                                                                         @Qualifier("geolocationDecoder") GeolocationDatabaseFeignErrorDecoder geolocationDatabaseFeignErrorDecoder,
                                                                         @Qualifier("prodGeoDbApiContext") GeolocationApiConfig.GeolocationContext openApiContext) {
        log.info("Creating GeoLocationDatabaseFeignClient: DEV");
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(geolocationDatabaseFeignErrorDecoder)
                .target(GeoLocationDatabaseFeignClient.class, openApiContext.getUrl());
    }

    @Bean
    @Profile("dev")
    public GeoDBOpenAPIFeignClient devGeoDBOpenAPIFeignClient(@Qualifier("locationObjectMapper") final ObjectMapper objectMapper,
                                                              @Qualifier("geolocationDecoder") GeolocationDatabaseFeignErrorDecoder geolocationDatabaseFeignErrorDecoder,
                                                              @Qualifier("devGeoOpenApiContext") GeolocationApiConfig.GeolocationContext openApiContext) {
        log.info("Creating GeoDBOpenAPIFeignClient: DEV");
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(geolocationDatabaseFeignErrorDecoder)
                .target(GeoDBOpenAPIFeignClient.class, openApiContext.getUrl());
    }

    @Bean
    @Profile("prod")
    public GeoDBOpenAPIFeignClient geoDBOpenAPIFeignClient(@Qualifier("locationObjectMapper") final ObjectMapper objectMapper,
                                                           @Qualifier("geolocationDecoder") GeolocationDatabaseFeignErrorDecoder geolocationDatabaseFeignErrorDecoder,
                                                           @Qualifier("devGeoOpenApiContext") GeolocationApiConfig.GeolocationContext openApiContext) {
        log.info("Creating GeoDBOpenAPIFeignClient: PROD");
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(geolocationDatabaseFeignErrorDecoder)
                .target(GeoDBOpenAPIFeignClient.class, openApiContext.getUrl());
    }
}
