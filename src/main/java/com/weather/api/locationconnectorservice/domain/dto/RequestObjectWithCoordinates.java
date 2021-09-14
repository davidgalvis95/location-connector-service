package com.weather.api.locationconnectorservice.domain.dto;


import lombok.*;

@Data
@AllArgsConstructor
@Builder
@ToString
public class RequestObjectWithCoordinates extends RequestObject {

    final String apiKey;

    final String apiHost;

    final Double latitude;

    final Double longitude;

    final Integer radius;
}
