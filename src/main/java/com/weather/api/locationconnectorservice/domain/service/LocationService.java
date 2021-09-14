package com.weather.api.locationconnectorservice.domain.service;

import com.weather.api.locationconnectorservice.domain.dto.LocationDto;
import com.weather.api.locationconnectorservice.domain.model.Location;
import com.weather.api.locationconnectorservice.infrastructure.exception.LocationNotFoundException;
import com.weather.api.locationconnectorservice.infrastructure.gatewaymanager.GatewayManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class LocationService {

    private final GatewayManager locationGatewayManager;

    @Autowired
    protected LocationService(final GatewayManager locationGatewayManager) {
        this.locationGatewayManager = locationGatewayManager;
    }

    public Location processRequest(final Double latitude, final Double longitude){

        try {
            final LocationDto locationDto = locationGatewayManager.buildRequestAndGetLocation(latitude, longitude).orElseThrow(() -> new RuntimeException("The location was not found"));
            //TODO use specialized translators to translate from the types received into the Location model

            return null;
        }catch (Exception exception){
            throw new LocationNotFoundException("Unable to get location from the external API server");
        }
    }
}
