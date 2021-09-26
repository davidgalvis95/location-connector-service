package com.weather.api.locationconnectorservice.infrastructure.gatewaymanager;

import com.weather.api.locationconnectorservice.domain.dto.Request;
import com.weather.api.locationconnectorservice.domain.dto.simplelocation.SimpleLocationDto;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoAPIClient;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoLocationDatabaseFeignClient;
import com.weather.api.locationconnectorservice.infrastructure.exception.LocationNotFoundException;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

@NoArgsConstructor
public class SimpleLocationGatewayManager extends LocationGatewayManager<SimpleLocationDto>{

    @Override
    public SimpleLocationDto getLocation(final Request<?> request, GeoAPIClient client) {
        return ( (GeoLocationDatabaseFeignClient) client).getApproximatedLocation();
    }

    public Pair<Double, Double> getCoordinates(final Optional<SimpleLocationDto> location) throws LocationNotFoundException{
        return location.map(simpleLocationDto -> Pair.of(simpleLocationDto.getLatitude(), simpleLocationDto.getLongitude()))
                .orElseThrow(() -> { throw new LocationNotFoundException("No coordinates found to map from"); });
    }
}
