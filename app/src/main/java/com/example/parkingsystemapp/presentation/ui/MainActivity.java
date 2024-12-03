package com.example.parkingsystemapp.presentation.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import com.example.parkingsystemapp.R;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Configurare OSM
        Configuration.getInstance().setUserAgentValue(getPackageName());

        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.map);

        // Configurare hartă
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true); // Permite interacțiunea multitouch

        // Centrare pe o locație
        GeoPoint startPoint = new GeoPoint(47.0105, 28.8638); // Chișinău, de exemplu
        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(startPoint);

        // Adăugare marker
        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(new GeoPoint(47.0105, 28.8638)); // Locație specifică
        startMarker.setTitle("Parcare Centrală");
        startMarker.setSubDescription("Adresa: Strada Principală, 5, Chișinău");
        startMarker.setSnippet("Locuri disponibile: 20");

// Adaugă un listener pentru click
        startMarker.setOnMarkerClickListener((marker, mapView) -> {
            // Afișare informație marker într-un Toast (sau alte acțiuni)
            Toast.makeText(MainActivity.this,
                    "Marker apăsat: " + marker.getTitle() + "\n" + marker.getPosition(),
                    Toast.LENGTH_LONG).show();

            // Poți returna `true` dacă dorești să consumi evenimentul
            return true;
        });

// Adaugă markerul pe hartă
        mapView.getOverlays().add(startMarker);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
}
