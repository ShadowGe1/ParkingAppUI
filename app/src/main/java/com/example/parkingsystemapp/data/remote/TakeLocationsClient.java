package com.example.parkingsystemapp.data.remote;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.parkingsystemapp.data.local.CoordinatePlacer;
import com.example.parkingsystemapp.manager.LocationsManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TakeLocationsClient {

    private static final String LOCATION_URL = "http://192.168.0.127:8080/markerPlacer";
    private static final String TAG = "TakeLocationsClient";

    public interface LocationsCallback {
        void onResponse(List<CoordinatePlacer> locations);
    }

    public void execute(LocationsCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            OkHttpClient client = new OkHttpClient();
            Response response = null;
            try {
                Request request = new Request.Builder()
                        .url(LOCATION_URL)
                        .get()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .build();

                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Request failed: " + response.code());
                    new Handler(Looper.getMainLooper()).post(() -> callback.onResponse(null));
                    return;
                }


                String responseBody = response.body() != null ? response.body().string() : null;
                if (responseBody != null) {

                    JSONObject jsonResponse = new JSONObject(responseBody);
                    LocationsManager locationsManager = new LocationsManager();
                    List<CoordinatePlacer> coordinates = locationsManager.jsonExtractionCoordinate(jsonResponse);

                    // Transmite JSON-ul obținut prin callback
                    new Handler(Looper.getMainLooper()).post(() -> callback.onResponse(coordinates));
                }
            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error during request", e);
                new Handler(Looper.getMainLooper()).post(() -> callback.onResponse(null));
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        });
    }
}
