package com.example.parkingsystemapp.presentation.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parkingsystemapp.R;
import com.example.parkingsystemapp.data.local.CoordinatePlacer;
import com.example.parkingsystemapp.data.remote.TakeLocationsClient;
import com.example.parkingsystemapp.manager.MapManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MapManager mapManager;

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

        mapManager = new MapManager(this, findViewById(R.id.map));
        mapManager.setup();

        TakeLocationsClient takeLocationsClient = new TakeLocationsClient();
        takeLocationsClient.execute((List<CoordinatePlacer> coordinates) -> {
            for(CoordinatePlacer coordinate : coordinates) {
                mapManager.addMarker(coordinate.getLatitude(), coordinate.getLongitude(), "1");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapManager.onResume();
    }
}
