package com.weather.api.locationconnectorservice.infrastructure.client;

import com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates.LocationFromCoordinatesDto;
import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.Map;

@Headers({"Accept: application/json",
        "Content-Type: application/json"})
public interface GeoDBOpenAPIFeignClient extends GeoAPIClient {

    @RequestLine("GET /v1/geo/locations/{location}/nearbyCities?radius={customRadius}")
    LocationFromCoordinatesDto getLocationFromCoordinates(@HeaderMap final Map<String, String> headers,
                                                          @Param("location") final String location,
                                                          @Param("customRadius") final int customRadius);
}
