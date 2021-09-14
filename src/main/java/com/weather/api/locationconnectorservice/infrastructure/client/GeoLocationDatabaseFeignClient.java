package com.weather.api.locationconnectorservice.infrastructure.client;

import com.weather.api.locationconnectorservice.domain.dto.simplelocation.SimpleLocationDto;
import feign.Headers;
import feign.RequestLine;

@Headers( { "Accept: application/json",
        "Content-Type: application/json" } )
public interface GeoLocationDatabaseFeignClient extends GeoAPIClient {

    @RequestLine( "GET" )
    SimpleLocationDto getApproximatedLocation();
}
