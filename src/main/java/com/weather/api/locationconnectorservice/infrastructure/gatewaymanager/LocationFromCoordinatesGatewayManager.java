package com.weather.api.locationconnectorservice.infrastructure.gatewaymanager;

import com.weather.api.locationconnectorservice.domain.dto.Request;
import com.weather.api.locationconnectorservice.domain.dto.RequestObject;
import com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates.LocationFromCoordinatesDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
public class LocationFromCoordinatesGatewayManager extends LocationGatewayManager<LocationFromCoordinatesDto>{

    @Override
    public LocationFromCoordinatesDto getLocation(Request<?> request) {
        return null;
    }


//    @Override
//    public LocationFromCoordinatesDto getLocation(final Request<RequestObjectWithCoordinates> request){
//        return LocationFromCoordinatesDto.builder().build();
//    }
}
