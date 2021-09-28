package com.weather.api.locationconnectorservice.domain.service;

import com.weather.api.locationconnectorservice.domain.dto.LocationDto;
import com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates.LocationFromCoordinatesDto;
import com.weather.api.locationconnectorservice.domain.dto.simplelocation.SimpleLocationDto;
import com.weather.api.locationconnectorservice.domain.model.Location;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoAPIClient;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoDBOpenAPIFeignClient;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoLocationDatabaseFeignClient;
import com.weather.api.locationconnectorservice.infrastructure.config.GeolocationApiConfig;
import com.weather.api.locationconnectorservice.infrastructure.exception.InconvertibleLocationTypeException;
import com.weather.api.locationconnectorservice.infrastructure.gatewaymanager.GatewayManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class LocationService {

    private final GatewayManager locationGatewayManager;

    private final Set<GeoAPIClient> locationGatewayClients;

    private Set<GeolocationApiConfig.GeolocationContext> geolocationContexts;

    @Autowired
    public LocationService(@Qualifier("locationGatewayManager") final GatewayManager locationGatewayManager,
                           @Qualifier("geolocationContexts") final Set<GeolocationApiConfig.GeolocationContext> geolocationContexts,
                           final GeoLocationDatabaseFeignClient geoLocationDatabaseFeignClient,
                           final GeoDBOpenAPIFeignClient geoDBOpenAPIFeignClient) {
        this.locationGatewayManager = locationGatewayManager;
        this.locationGatewayClients = Set.of(geoLocationDatabaseFeignClient, geoDBOpenAPIFeignClient);
        this.geolocationContexts = geolocationContexts;
    }

    public Location processRequest(final Double latitude, final Double longitude) throws RuntimeException {

        try {
            return locationGatewayManager.buildRequestAndGetLocation(latitude, longitude, locationGatewayClients, geolocationContexts)
                    .map(locationDto -> Pair.of(locationDto, locationDto.getClass()))
                    .map(locationPair -> mapFromLocationDtoToLocation(locationPair.getLeft(), locationPair.getRight()))
                    .orElseThrow(() -> new RuntimeException("The location was not found"));
        } catch (Exception exception) {
            throw new RuntimeException("Unable to process the request for location", exception.getCause());
        }
    }

    private static <T extends LocationDto> Location mapFromLocationDtoToLocation(final LocationDto locationDto, final Class<T> clazz)
            throws InconvertibleLocationTypeException {

        final Location location = new Location();

        if (LocationFromCoordinatesDto.class.equals(clazz)) {
            location.translateFromSimpleLocationDto((LocationFromCoordinatesDto) locationDto);
        } else if (SimpleLocationDto.class.equals(clazz)) {
            location.translateFromSimpleLocationDto((SimpleLocationDto) locationDto);
        } else {
            log.error("The location of type: {} cannot be converted into any of the following destination types: {}, {}",
                    locationDto.getClass().toString(), LocationFromCoordinatesDto.class, SimpleLocationDto.class);
            throw new InconvertibleLocationTypeException("The location from the api response cannot be converted into any of the destination types");
        }
        return location;
    }
}
