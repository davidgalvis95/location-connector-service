package com.weather.api.locationconnectorservice.domain.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Request<T> {

    T payload;
}
