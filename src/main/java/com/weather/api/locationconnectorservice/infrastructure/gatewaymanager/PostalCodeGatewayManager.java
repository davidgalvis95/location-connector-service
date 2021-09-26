package com.weather.api.locationconnectorservice.infrastructure.gatewaymanager;

import org.springframework.stereotype.Component;

@Component
public class PostalCodeGatewayManager{

    public String getPostalCode(final Double lat, final Double lon){
        return "111031";
    }
}
