package com.weather.api.locationconnectorservice.domain.dto;

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

//    TODO this can be converted into enum
    String type;

    String city;

    String name;

    String country;

    String countryCode;

    String region;

    String regionCode;

    Long latitude;

    Long longitude;

    Long population;

    Double distance;

}
