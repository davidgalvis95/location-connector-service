package com.weather.api.locationconnectorservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
@AllArgsConstructor
public class RelatedLinkDto {

    String rel;

    @JsonProperty("href")
    String link;
}
