package com.weather.api.locationconnectorservice.domain.model;

import com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates.LocationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.LinkedList;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@ToString
public class Location {

    final UUID id;

    final LocationType type;

    final String locationName;

    final String country;

    final String countryCode;

    final String region;

    final String regionCode;

    final Double latitude;

    final Double longitude;

    final Integer population;

    final Double distance;

    final String postalCode;

    final LinkedList<Location> nearestLocations;
}
