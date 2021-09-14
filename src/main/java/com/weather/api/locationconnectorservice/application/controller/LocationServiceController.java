package com.weather.api.locationconnectorservice.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/location")
public class LocationServiceController {


    @PostMapping()
    public ResponseEntity<Void> generateLocation(@RequestParam final Long longitude, @RequestParam final Long latitude){
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

//    @GetMapping
//    public LocationDto getCurrentLocation() {
//
//        return LocationDto.builder().build();
//    }
}
