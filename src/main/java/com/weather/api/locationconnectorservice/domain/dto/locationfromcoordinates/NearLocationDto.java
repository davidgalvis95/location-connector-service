package com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;


@Value
@Builder(toBuilder = true)
@ToString
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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

    String postalCode;

}
