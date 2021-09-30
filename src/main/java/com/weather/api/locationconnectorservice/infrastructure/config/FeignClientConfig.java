package com.weather.api.locationconnectorservice.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.api.locationconnectorservice.domain.model.GeolocationApiContext;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoDBOpenAPIFeignClient;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoLocationDatabaseFeignClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Set;

@Slf4j
@Configuration
public class FeignClientConfig {

    @Bean("geolocationDecoder")
    public GeolocationDatabaseFeignErrorDecoder geolocationDatabaseFeignErrorDecoder() {
        return new GeolocationDatabaseFeignErrorDecoder();
    }


    @Bean
    public GeoLocationDatabaseFeignClient geoLocationDatabaseFeignClient(@Qualifier("locationObjectMapper") final ObjectMapper objectMapper,
                                                                         @Qualifier("geolocationDecoder") GeolocationDatabaseFeignErrorDecoder geolocationDatabaseFeignErrorDecoder,
                                                                         final Set<GeolocationApiConfig.GeolocationContext> contexts) {
        log.info("Creating GeoLocationDatabaseFeignClient");

        final String geoOpenApiContextUrl = contexts.stream()
                .filter(context ->  context.getName().equals(GeolocationApiContext.GEOLOCATION_DATABASE.getValue()))
                .findFirst()
                .map( GeolocationApiConfig.GeolocationContext::getUrl )
                .orElse(null);

        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(geolocationDatabaseFeignErrorDecoder)
                .target(GeoLocationDatabaseFeignClient.class, geoOpenApiContextUrl);
    }

    @Bean
    public GeoDBOpenAPIFeignClient geoDBOpenAPIFeignClient(@Qualifier("locationObjectMapper") final ObjectMapper objectMapper,
                                                           @Qualifier("geolocationDecoder") GeolocationDatabaseFeignErrorDecoder geolocationDatabaseFeignErrorDecoder,
                                                           final Set<GeolocationApiConfig.GeolocationContext> contexts) {
        log.info("Creating GeoDBOpenAPIFeignClient");

        final String geoDatabaseUrl = contexts.stream()
                .filter(context ->  context.getName().equals(GeolocationApiContext.GEOLOCATION_OPEN_API.getValue()))
                .findFirst()
                .map( GeolocationApiConfig.GeolocationContext::getUrl )
                .orElse(null);

        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(geolocationDatabaseFeignErrorDecoder)
                .target(GeoDBOpenAPIFeignClient.class, geoDatabaseUrl);
    }
}
