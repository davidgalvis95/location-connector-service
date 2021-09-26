package com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.weather.api.locationconnectorservice.domain.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
@ToString
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationFromCoordinatesDto implements LocationDto {

    @JsonProperty("data")
    List<NearLocationDto> nearestLocations;

    List<RelatedLinkDto> links;

    LocationMetadataDto metadata;
}
