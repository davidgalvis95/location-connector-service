package com.weather.api.locationconnectorservice.infrastructure.gatewaymanager;

import com.weather.api.locationconnectorservice.domain.dto.Request;
import com.weather.api.locationconnectorservice.domain.dto.RequestObjectWithCoordinates;
import com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates.LocationFromCoordinatesDto;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoAPIClient;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoDBOpenAPIFeignClient;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
public class LocationFromCoordinatesGatewayManager extends LocationGatewayManager<LocationFromCoordinatesDto> {

    @Override
    public LocationFromCoordinatesDto getLocation(final Request<?> request, final GeoAPIClient client) {
        final Request<RequestObjectWithCoordinates> requestObjectWithCoordinatesRequest = (Request<RequestObjectWithCoordinates>) request;
        return Optional.ofNullable(requestObjectWithCoordinatesRequest.getPayload())
                .map(requestBody -> {
                    final String location = requestBody.getLatitude().toString().concat(requestBody.getLongitude().toString());
                    return ((GeoDBOpenAPIFeignClient) client).getLocationFromCoordinates(buildRequestHeaders(requestBody.getApiHost(), requestBody.getApiKey()),
                            location,
                            requestBody.getRadius());
                }).orElse(null);
    }

    public String getPostalCodeFromCoordinates(final Double latitude, final Double longitude, PostalCodeGatewayManager postalCodeGatewayManager) {
        return postalCodeGatewayManager.getPostalCode(latitude, longitude);
    }

    private Map<String, String> buildRequestHeaders(final String apiHost, final String apiKey) {
        return Map.of("x-rapidapi-host", apiHost, "x-rapidapi-key", apiKey);
    }
}
