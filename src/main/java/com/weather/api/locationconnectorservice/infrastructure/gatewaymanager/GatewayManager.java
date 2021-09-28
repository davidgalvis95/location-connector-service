package com.weather.api.locationconnectorservice.infrastructure.gatewaymanager;

import com.weather.api.locationconnectorservice.domain.dto.LocationDto;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoAPIClient;
import com.weather.api.locationconnectorservice.infrastructure.config.GeolocationApiConfig;

import java.util.Optional;
import java.util.Set;

public interface GatewayManager {

    Optional<LocationDto> buildRequestAndGetLocation(final Double latitude,
                                                     final Double longitude,
                                                     final Set<GeoAPIClient> locationGatewayClients,
                                                     final Set<GeolocationApiConfig.GeolocationContext> geolocationContexts);
}
