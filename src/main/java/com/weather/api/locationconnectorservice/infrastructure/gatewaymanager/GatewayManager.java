package com.weather.api.locationconnectorservice.infrastructure.gatewaymanager;

import com.weather.api.locationconnectorservice.domain.dto.LocationDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface GatewayManager {

    Optional<LocationDto> buildRequestAndGetLocation(final Double latitude, final Double longitude);
}
