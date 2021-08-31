package com.weather.api.locationconnectorservice.domain.service;

import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class LocationService {

    public void processLocation(final Long latitude, final Long longitude){

        final boolean areThereCoordinates = Stream.of(Optional.ofNullable(latitude), Optional.ofNullable(longitude))
                .flatMap(Optional::stream)
                .allMatch(Objects::nonNull);

        if(areThereCoordinates){

        }
    }

    private void processLocationFromCoordinates(final Long latitude, final Long longitude){

    }

    private void processLocationWithoutPriorInfo(){

    }
}
