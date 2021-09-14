package com.weather.api.locationconnectorservice.infrastructure.exception;

public class LocationNotFoundException extends RuntimeException{

    public LocationNotFoundException(final String message, final Throwable cause){
        super(message, cause);
    }

    public LocationNotFoundException(final String message){
        super(message);
    }
}
