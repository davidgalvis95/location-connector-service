package com.weather.api.locationconnectorservice.domain.dto.locationfromcoordinates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
@AllArgsConstructor
public class LocationMetadataDto {

    Integer currentOffset;

    Long totalCount;

}
