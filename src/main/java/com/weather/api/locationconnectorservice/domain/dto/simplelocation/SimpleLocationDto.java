package com.weather.api.locationconnectorservice.domain.dto.simplelocation;

import com.weather.api.locationconnectorservice.domain.dto.LocationDto;
import lombok.*;

import java.math.BigDecimal;

@Value
@Builder
@ToString
@AllArgsConstructor
public class SimpleLocationDto implements LocationDto {

    String countryCode;

    String countryName;

    String city;

    String postal;

    Double longitude;

    Double latitude;

    String IPv4;

    String state;

}
