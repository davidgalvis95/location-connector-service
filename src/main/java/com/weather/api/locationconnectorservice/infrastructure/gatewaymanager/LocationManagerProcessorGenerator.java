package com.weather.api.locationconnectorservice.infrastructure.gatewaymanager;

import com.weather.api.locationconnectorservice.domain.dto.LocationDto;
import com.weather.api.locationconnectorservice.domain.dto.RequestObjectWithCoordinates;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
public class LocationManagerProcessorGenerator {

    public static final Map<Class<?>,LocationGatewayManager<? extends LocationDto>> LOCATION_PROCESSORS = Map.ofEntries(
                    Map.entry(Void.class, new SimpleLocationGatewayManager()),
                    Map.entry(RequestObjectWithCoordinates.class, new LocationFromCoordinatesGatewayManager()));

}
