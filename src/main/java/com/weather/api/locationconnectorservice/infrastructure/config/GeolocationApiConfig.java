package com.weather.api.locationconnectorservice.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;
import java.util.Set;

@Configuration
@ConfigurationProperties("location.api")
public class GeolocationApiConfig {

    private Map<String, GeolocationContext> geolocations;
    private String env;

    @Data
    public static class GeolocationContext {
        private String url;
        private String host;
        private String apiKey;
    }

    @Profile("dev")
    @Bean
    public GeolocationContext devGeoOpenApiContext() {
        return geolocations.get("geolocation-open");
    }

    @Profile("prod")
    @Bean
    public GeolocationContext prodGeoOpenApiContext() {
        return geolocations.get("geolocation-open");
    }

    @Profile("dev")
    @Bean
    public GeolocationContext devGeoDbApiContext() {
        return geolocations.get("geolocation-db");
    }

    @Profile("prod")
    @Bean
    public GeolocationContext prodGeoDbApiContext() {
        return geolocations.get("geolocation-db");
    }

    @Bean
    public Set<GeolocationContext> geolocationContexts(final GeolocationContext devGeoOpenApiContext,
                                                       final GeolocationContext devGeoDbApiContext,
                                                       final GeolocationContext prodGeoOpenApiContext,
                                                       final GeolocationContext prodGeoDbApiContext) {
        return env.equals("dev") ? Set.of(devGeoOpenApiContext, devGeoDbApiContext): Set.of(prodGeoOpenApiContext, prodGeoDbApiContext);
    }
}
