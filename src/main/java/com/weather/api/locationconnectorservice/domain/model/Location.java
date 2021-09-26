package com.weather.api.locationconnectorservice.domain.model;

import com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates.LocationFromCoordinatesDto;
import com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates.LocationType;
import com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates.NearLocationDto;
import com.weather.api.locationconnectorservice.domain.dto.simplelocation.SimpleLocationDto;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@ToString
public class Location {

    public Location() {
        this.id = null;
        this.type = null;
        this.locationName = null;
        this.country = null;
        this.countryCode = null;
        this.region = null;
        this.regionCode = null;
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.population = 0;
        this.distance = 0.0;
        this.postalCode = null;
        this.nearestLocations = null;
    }

    UUID id;

    LocationType type;

    String locationName;

    String country;

    String countryCode;

    String region;

    String regionCode;

    Double latitude;

    Double longitude;

    Integer population;

    Double distance;

    String postalCode;

    LinkedList<Location> nearestLocations;

    public void translateFromSimpleLocationDto(final LocationFromCoordinatesDto locationFromCoordinatesDto) {

        if (Objects.nonNull(locationFromCoordinatesDto.getNearestLocations())) {

            Optional<NearLocationDto> nearestLocationDto = locationFromCoordinatesDto.getNearestLocations().stream()
                    .filter(Objects::nonNull)
                    .min(Comparator.comparing(NearLocationDto::getDistance));

            nearestLocationDto.ifPresent(location -> {
                fromLocationFromCoordinatesDtoToLocation(location);
                this.nearestLocations = locationFromCoordinatesDto.getNearestLocations().stream()
                        .filter(Objects::nonNull)
                        .filter(locationDto -> !locationDto.equals(nearestLocationDto.get()))
                        .map(this::fromFromNearLocationDtoToLocation)
                        .collect(Collectors.toCollection(LinkedList::new));
            });
        }
    }

    private void fromLocationFromCoordinatesDtoToLocation(final NearLocationDto nearLocationDto) {
        this.id = UUID.randomUUID();
        this.type = nearLocationDto.getType();
        this.locationName = nearLocationDto.getName();
        this.country = nearLocationDto.getCountry();
        this.countryCode = nearLocationDto.getCountryCode();
        this.region = nearLocationDto.getRegion();
        this.regionCode = nearLocationDto.getRegionCode();
        this.latitude = nearLocationDto.getLatitude();
        this.longitude = nearLocationDto.getLongitude();
        this.population = nearLocationDto.getPopulation();
        this.distance = nearLocationDto.getDistance();
        this.postalCode = nearLocationDto.getPostalCode();
    }

    private Location fromFromNearLocationDtoToLocation(final NearLocationDto nearLocationDto) {
        return Location.builder()
                .id(UUID.randomUUID())
                .type(nearLocationDto.getType())
                .locationName(nearLocationDto.getName())
                .country(nearLocationDto.getCountry())
                .countryCode(nearLocationDto.getCountryCode())
                .region(nearLocationDto.getRegion())
                .regionCode(nearLocationDto.getRegionCode())
                .latitude(nearLocationDto.getLatitude())
                .longitude(nearLocationDto.getLongitude())
                .population(nearLocationDto.getPopulation())
                .distance(nearLocationDto.getDistance())
                .postalCode(nearLocationDto.getPostalCode())
                .build();
    }

    public void translateFromSimpleLocationDto(final SimpleLocationDto simpleLocationDto) {
        this.id = UUID.randomUUID();
        this.locationName = simpleLocationDto.getCity();
        this.country = simpleLocationDto.getCountryName();
        this.countryCode = simpleLocationDto.getCountryCode();
        this.region = simpleLocationDto.getState();
        this.latitude = simpleLocationDto.getLatitude();
        this.longitude = simpleLocationDto.getLongitude();
        this.postalCode = simpleLocationDto.getPostal();
    }
}
