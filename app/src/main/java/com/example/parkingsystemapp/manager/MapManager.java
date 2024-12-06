package com.example.parkingsystemapp.manager;

import static org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK;

import android.content.Context;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapManager {
    private final MapView mapView;
    private final Context context;

    public MapManager(Context context, MapView mapView) {
        this.mapView = mapView;
        this.context = context;

        Configuration.getInstance().setUserAgentValue(context.getPackageName());
    }

    public void setup(){
        mapView.setTileSource(MAPNIK);
        mapView.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(47.0105, 28.8638);
        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(startPoint);
    }

    public void addMarker(double latitude, double longitude, String title) {
        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(latitude, longitude));
        marker.setTitle(title);

        marker.setOnMarkerClickListener((m, map) -> {
            Toast.makeText(context,
                    "Marker apasat" + m.getTitle() + " " + m.getPosition(),
                    Toast.LENGTH_SHORT).show();
            return true;
        });

        mapView.getOverlays().add(marker);
    }

    public void onPause() {
        mapView.onPause();
    }

    public void onResume() {
        mapView.onResume();
    }
}
