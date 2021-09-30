package com.weather.api.locationconnectorservice.infrastructure.gatewaymanager;

import com.weather.api.locationconnectorservice.domain.dto.LocationDto;
import com.weather.api.locationconnectorservice.domain.dto.Request;
import com.weather.api.locationconnectorservice.domain.dto.RequestObjectWithCoordinates;
import com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates.LocationFromCoordinatesDto;
import com.weather.api.locationconnectorservice.domain.model.GeolocationApiContext;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoAPIClient;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoDBOpenAPIFeignClient;
import com.weather.api.locationconnectorservice.infrastructure.client.GeoLocationDatabaseFeignClient;
import com.weather.api.locationconnectorservice.infrastructure.config.GeolocationApiConfig;
import com.weather.api.locationconnectorservice.infrastructure.exception.LocationNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Slf4j
@Component("locationGatewayManager")
@NoArgsConstructor
public class LocationGatewayManager<T extends LocationDto> implements GatewayManager {

    private static final Integer DISTANCE_RADIUS = 100;

    private PostalCodeGatewayManager postalCodeGatewayManager;

    @Autowired
    public LocationGatewayManager(final PostalCodeGatewayManager postalCodeGatewayManager) {
        this.postalCodeGatewayManager = postalCodeGatewayManager;
    }

    public Optional<LocationDto> buildRequestAndGetLocation(final Double latitude,
                                                            final Double longitude,
                                                            final Set<GeoAPIClient> locationGatewayClients,
                                                            final Set<GeolocationApiConfig.GeolocationContext> geolocationContexts) throws MissingResourceException {

        RequestObjectWithCoordinates requestBody = RequestObjectWithCoordinates.builder().build();
        final boolean areThereCoordinates = Stream.of(Optional.ofNullable(latitude), Optional.ofNullable(longitude))
                .flatMap(Optional::stream)
                .allMatch(Objects::nonNull);

        if (areThereCoordinates) {
            Iterator<GeolocationApiConfig.GeolocationContext> iterator = geolocationContexts.iterator();

            while (iterator.hasNext()) {
                if (iterator.next().getName().equals(GeolocationApiContext.GEOLOCATION_OPEN_API.getValue())) {

                    requestBody = buildRequestFromCoordinates(iterator.next().getHost(),
                            iterator.next().getApiKey(),
                            latitude,
                            longitude,
                            DISTANCE_RADIUS);
                    break;
                }
            }


            final Request<RequestObjectWithCoordinates> requestWithCoordinates = new Request<>(requestBody);
            final LocationFromCoordinatesGatewayManager processor = (LocationFromCoordinatesGatewayManager) findLocationManagerProcessor(requestWithCoordinates);
            final LocationFromCoordinatesDto locationFromCoordinatesDto = processor.getLocation(requestWithCoordinates,
                    (GeoDBOpenAPIFeignClient) locationGatewayClients.stream().filter(client -> client instanceof GeoDBOpenAPIFeignClient));

            locationFromCoordinatesDto.toBuilder()
                    .nearestLocations(locationFromCoordinatesDto.getNearestLocations().stream()
                            .map(location -> location.toBuilder()
                                    .postalCode(processor.getPostalCodeFromCoordinates(location.getLatitude(), location.getLongitude(), postalCodeGatewayManager))
                                    .build())
                            .collect(Collectors.toList()));

            return Optional.of(locationFromCoordinatesDto);
        }

        final Request<Void> emptyRequest = new Request<>();
        final LocationGatewayManager<T> processor = (LocationGatewayManager<T>) findLocationManagerProcessor(emptyRequest);

        try {
            final Pair<Double, Double> location = chainedLogicForSimpleLocation(processor::getLocation, processor::getCoordinates)
                    .apply(emptyRequest, (GeoLocationDatabaseFeignClient) locationGatewayClients.stream().filter(client -> client instanceof GeoLocationDatabaseFeignClient));
            try {
                buildRequestAndGetLocation(location.getLeft(), location.getRight(), locationGatewayClients, geolocationContexts);
            } catch (Exception exception) {
                log.warn("The following error happened when trying to get a more accurate location: {}, returning simple approximated location", exception.getMessage());
                return Optional.of(processor.getLocation(emptyRequest, (GeoLocationDatabaseFeignClient) locationGatewayClients.stream().filter(client -> client instanceof GeoLocationDatabaseFeignClient)));
            }
        } catch (LocationNotFoundException e) {
            throw new MissingResourceException("Not enough data for location", "SimpleLocationDto", "latitude and longitude");
        }
        return Optional.empty();
    }

    private BiFunction<Request<?>, GeoAPIClient, Pair<Double, Double>> chainedLogicForSimpleLocation(final BiFunction<Request<?>, GeoAPIClient, T> getLocationFunction,
                                                                                                     final Function<Optional<T>, Pair<Double, Double>> extractCoordinatesFunction) {
        final Function<T, Optional<T>> optionalOf = Optional::ofNullable;
        return getLocationFunction.andThen(optionalOf)
                .andThen(extractCoordinatesFunction);
    }

    private LocationGatewayManager<? extends LocationDto> findLocationManagerProcessor(final Request<?> request) {
        return LocationManagerProcessorGenerator.LOCATION_PROCESSORS.get(request.getPayload().getClass());
    }

    public T getLocation(final Request<?> request, final GeoAPIClient client) {
        return null;
    }

    ;

    private Pair<Double, Double> getCoordinates(final Optional<T> optionalLocation) {
        return null;
    }

    private static RequestObjectWithCoordinates buildRequestFromCoordinates(final String apiHost,
                                                                            final String apiKey,
                                                                            final Double latitude,
                                                                            final Double longitude,
                                                                            final Integer radius) {
        return RequestObjectWithCoordinates.builder()
                .apiHost(apiHost)
                .apiKey(apiKey)
                .latitude(latitude)
                .longitude(longitude)
                .radius(radius)
                .build();
    }
}
