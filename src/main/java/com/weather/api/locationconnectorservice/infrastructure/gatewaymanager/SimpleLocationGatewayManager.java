package com.weather.api.locationconnectorservice.infrastructure.gatewaymanager;

import com.weather.api.locationconnectorservice.domain.dto.Request;
import com.weather.api.locationconnectorservice.domain.dto.RequestObject;
import com.weather.api.locationconnectorservice.domain.dto.simplelocation.SimpleLocationDto;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoLocationDatabaseFeignClient;
import com.weather.api.locationconnectorservice.infrastructure.exception.LocationNotFoundException;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@NoArgsConstructor
public class SimpleLocationGatewayManager extends LocationGatewayManager<SimpleLocationDto>{

    @Autowired
    private GeoLocationDatabaseFeignClient geoLocationDatabaseFeignClient;

    @Override
    public SimpleLocationDto getLocation(final Request<?> request) {
        return geoLocationDatabaseFeignClient.getApproximatedLocation();
    }

    public Pair<Double, Double> getCoordinates(final Optional<SimpleLocationDto> location) throws LocationNotFoundException{
        return location.map(simpleLocationDto -> Pair.of(simpleLocationDto.getLatitude(), simpleLocationDto.getLongitude()))
                .orElseThrow(() -> { throw new LocationNotFoundException("No coordinates found to map from"); });
    }
}
