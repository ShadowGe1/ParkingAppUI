package com.example.parkingsystemapp.manager;

import android.util.Log;

import com.example.parkingsystemapp.data.local.CoordinatePlacer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationsManager {
    public static final String TAG = "LocationsManager";
    public List<CoordinatePlacer> jsonExtractionCoordinate(JSONObject locations) {

        List<CoordinatePlacer> coordinates = new ArrayList<>();

        try {
            JSONArray arrayLocations = locations.getJSONArray("locations");

            for(int i = 0; i < arrayLocations.length(); i++) {
                JSONObject location = arrayLocations.getJSONObject(i);

                double latitude = location.getDouble("latitude");
                double longitude = location.getDouble("longitude");

                CoordinatePlacer coordinatePlacer = new CoordinatePlacer(latitude, longitude);
                coordinates.add(coordinatePlacer);
            }
        } catch (Exception e) {
            Log.e(TAG,"Failed to extract locations from json");
        }

        return coordinates;
    }
}
