package com.weather.api.locationconnectorservice.domain.dto;

import lombok.*;

@Value
@Builder
@ToString
@AllArgsConstructor
public class LocationDto {

    String countryCode;

    String countryName;

    String city;

    String postal;

    String longitude;

    String latitude;

    String IPv4;

    String state;

}
