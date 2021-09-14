package com.weather.api.locationconnectorservice.infrastructure.client;

import com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates.LocationFromCoordinatesDto;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestHeader;

@Headers({"Accept: application/json",
        "Content-Type: application/json"})
public interface GeoDBOpenAPIFeignClient extends GeoAPIClient {

    @RequestLine("GET /v1/geo/locations/{location}/nearbyCities?radius={customRadius}")
    LocationFromCoordinatesDto getLocationFromCoordinates(@RequestHeader("x-rapidapi-host") final String apiHost,
                                                          @RequestHeader("x-rapidapi-key") final String apiKey,
                                                          @Param("location") final String location,
                                                          @Param("customRadius") final int customRadius);
}
