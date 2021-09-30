package com.weather.api.locationconnectorservice.domain.model;

public enum GeolocationApiContext {
    GEOLOCATION_OPEN_API("GeolocationOpenApi"),
    GEOLOCATION_DATABASE("GeolocationDatabase");

    private final String value;

    GeolocationApiContext(String value){
        this.value=value;
    }

    public String getValue(){
        return this.value;
    }
}
