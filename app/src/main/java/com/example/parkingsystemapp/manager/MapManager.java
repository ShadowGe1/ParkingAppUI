package com.example.parkingsystemapp.manager;

import static org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.example.parkingsystemapp.R;

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

        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable parkMarker = context.getDrawable(R.drawable.parking_marker3);

        marker.setIcon(parkMarker);

        marker.setOnMarkerClickListener((m, map) -> {
            Toast.makeText(context,
                    "Marker apasat" + m.getTitle() + " " + m.getPosition(),
                    Toast.LENGTH_SHORT).show();
            return true;
        });

        mapView.getOverlays().add(marker);
    }

    public void onPause() {
        if(mapView != null) {
            mapView.onPause();
        }
    }

    public void onResume() {
        if(mapView != null) {
            mapView.onResume();
        }
    }

    public void onDestroy() {
        if(mapView != null) {
            mapView.onDetach();
        }
    }
}
