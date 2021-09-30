package com.weather.api.locationconnectorservice.infrastructure.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Configuration
@ConfigurationProperties("location.api")
public class GeolocationApiConfig {

    private Map<String, GeolocationContext> geolocations;

    private String env;

    @Data
    public static class GeolocationContext {
        private String name;
        private String url;
        private String host;
        private String apiKey;
    }

    @Profile("dev")
    @Bean
    public GeolocationContext devGeoOpenApiContext() {
        return this.getGeolocations().get("geolocation-open");
    }

    @Profile("prod")
    @Bean
    public GeolocationContext prodGeoOpenApiContext() {
        return this.getGeolocations().get("geolocation-open");
    }

    @Profile("dev")
    @Bean
    public GeolocationContext devGeoDbApiContext() {
        return this.getGeolocations().get("geolocation-db");
    }

    @Profile("prod")
    @Bean
    public GeolocationContext prodGeoDbApiContext() {
        return this.getGeolocations().get("geolocation-db");
    }

    @Bean
    public Set<GeolocationContext> geolocationContexts(@Qualifier("devGeoOpenApiContext") final Optional<GeolocationContext> devGeoOpenApiContext,
                                                       @Qualifier("devGeoDbApiContext") final Optional<GeolocationContext> devGeoDbApiContext,
                                                       @Qualifier("prodGeoOpenApiContext") final Optional<GeolocationContext> prodGeoOpenApiContext,
                                                       @Qualifier("prodGeoDbApiContext") final Optional<GeolocationContext> prodGeoDbApiContext) {
        return Stream.of(devGeoOpenApiContext, devGeoDbApiContext, prodGeoOpenApiContext, prodGeoDbApiContext)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }
}
