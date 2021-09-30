package com.weather.api.locationconnectorservice.application.controller;

import com.weather.api.locationconnectorservice.domain.model.Location;
import com.weather.api.locationconnectorservice.domain.model.Response;
import com.weather.api.locationconnectorservice.domain.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/location")
public class LocationController {

    private final LocationService locationService;

    //TODO pass the b64 encoded user context and a valid apiKey to get the location
    @PostMapping()
    public ResponseEntity<Response<Location>> generateLocation(@RequestParam(name = "longitude", required = false) final Double longitude,
                                                               @RequestParam(name = "latitude", required = false) final Double latitude) {

        Response<Location> response = new Response<>();
        try {

            final Location location = locationService.processRequest(latitude, longitude);
            response.setPayload(location);
            response.setDateTime(OffsetDateTime.now());
            response.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(response);

        } catch (RuntimeException exception) {

            response.setPayload(Location.builder().build());
            response.setDateTime(OffsetDateTime.now());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.ok().body(response);
        }
    }

}
