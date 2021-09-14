package com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;


@Value
@Builder
@ToString
@AllArgsConstructor
public class NearLocationDto {

    Integer id;

    String wikiDataId;

    LocationType type;

    String city;

    String name;

    String country;

    String countryCode;

    String region;

    String regionCode;

    Double latitude;

    Double longitude;

    Integer population;

    Double distance;

}
