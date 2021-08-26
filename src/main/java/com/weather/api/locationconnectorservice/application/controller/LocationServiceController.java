package com.weather.api.locationconnectorservice.application.controller;

import com.weather.api.locationconnectorservice.domain.dto.LocationDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/location")
public class LocationServiceController {

    @GetMapping
    public LocationDto getCurrentLocation() {

        return LocationDto.builder().build();
    }
}
