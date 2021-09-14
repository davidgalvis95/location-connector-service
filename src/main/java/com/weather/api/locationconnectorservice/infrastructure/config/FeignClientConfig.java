package com.weather.api.locationconnectorservice.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoDBOpenAPIFeignClient;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoLocationDatabaseFeignClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "location")
public class FeignClientConfig {

    private final String geolocationApiUrl;
    private final String geoDBOpenApiUrl;

    public FeignClientConfig(@Value("${geolocation-db.url}") final String geolocationApiUrl,
                             @Value("${geo-db-open-api.url}") final String geoDBOpenApiUrl) {
        this.geolocationApiUrl = geolocationApiUrl;
        this.geoDBOpenApiUrl = geoDBOpenApiUrl;
    }

    @Bean("geolocationDecoder")
    public GeolocationDatabaseFeignErrorDecoder geolocationDatabaseFeignErrorDecoder() {
        return new GeolocationDatabaseFeignErrorDecoder();
    }

    @Bean
    public GeoLocationDatabaseFeignClient geoLocationDatabaseFeignClient(@Qualifier("locationObjectMapper") final ObjectMapper objectMapper,
                                                                         @Qualifier("geolocationDecoder") GeolocationDatabaseFeignErrorDecoder geolocationDatabaseFeignErrorDecoder) {
        log.info("Creating GeoLocationDatabaseFeignClient");
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(geolocationDatabaseFeignErrorDecoder)
                .target(GeoLocationDatabaseFeignClient.class, geolocationApiUrl);
    }

    @Bean
    public GeoDBOpenAPIFeignClient geoDBOpenAPIFeignClient(@Qualifier("locationObjectMapper") final ObjectMapper objectMapper,
                                                           @Qualifier("geolocationDecoder") GeolocationDatabaseFeignErrorDecoder geolocationDatabaseFeignErrorDecoder) {
        log.info("Creating GeoDBOpenAPIFeignClient");
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(geolocationDatabaseFeignErrorDecoder)
                .target(GeoDBOpenAPIFeignClient.class, geoDBOpenApiUrl);
    }
}
