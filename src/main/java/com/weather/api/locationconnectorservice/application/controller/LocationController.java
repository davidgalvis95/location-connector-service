package com.weather.api.locationconnectorservice.application.controller;

import com.weather.api.locationconnectorservice.domain.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/location")
public class LocationController {

    private final LocationService locationService;

    //TODO pass the b64 encoded user context and a valid apiKey to get the location
    @PostMapping()
    public ResponseEntity<Void> generateLocation(@RequestParam final Long longitude, final Long latitude){


        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
