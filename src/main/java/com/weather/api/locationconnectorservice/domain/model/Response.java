package com.weather.api.locationconnectorservice.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class Response<T>{

    T payload;

    String message;

    Integer statusCode;

    OffsetDateTime dateTime;
}
