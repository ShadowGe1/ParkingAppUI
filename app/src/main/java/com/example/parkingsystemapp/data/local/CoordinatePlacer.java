package com.example.parkingsystemapp.data.local;

public class CoordinatePlacer {
    private final double latitude;
    private final double longitude;

    public CoordinatePlacer(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
