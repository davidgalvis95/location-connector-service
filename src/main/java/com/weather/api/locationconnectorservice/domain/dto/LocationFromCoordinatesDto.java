package com.weather.api.locationconnectorservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Value
@Builder
@ToString
@AllArgsConstructor
public class LocationFromCoordinatesDto {

    @JsonProperty("data")
    List<NearLocationDto> nearestLocations;

    List<RelatedLinkDto> links;

    LocationMetadataDto metadata;
}
