package com.weather.api.locationconnectorservice.infrastructure.exception;

public class InconvertibleLocationTypeException extends ClassCastException{

    public InconvertibleLocationTypeException(final String message) {
        super(message);
    }
}
