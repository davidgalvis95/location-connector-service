package com.weather.api.locationconnectorservice.infrastructure.client;

import com.weather.api.locationconnectorservice.domain.dto.LocationDto;
import feign.Headers;
import feign.RequestLine;

@Headers( { "Accept: application/json",
        "Content-Type: application/json" } )
public interface LocationFeignClient {

    @RequestLine( "GET" )
    LocationDto getLeadFromNationalRegistry();
}
