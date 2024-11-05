package com.peter.amisy.weatherapi;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(String location) {
        super("Location not found for : " + location);
    }
}
