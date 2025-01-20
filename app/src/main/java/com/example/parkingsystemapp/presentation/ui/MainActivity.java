package com.example.parkingsystemapp.presentation.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.parkingsystemapp.FavoriteFragment;
import com.example.parkingsystemapp.MapFragment;
import com.example.parkingsystemapp.ProfileFragment;
import com.example.parkingsystemapp.R;
import com.example.parkingsystemapp.SearchFragment;
import com.example.parkingsystemapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private static final String TAG = "MainActivity";
    private Fragment mapFragment;
    private Fragment searchFragment;
    private Fragment favoriteFragment;
    private Fragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        replaceFragment(new MapFragment());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mapFragment = new MapFragment();
        searchFragment = new SearchFragment();
        favoriteFragment = new FavoriteFragment();
        profileFragment = new ProfileFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, mapFragment, "MAP_FRAGMENT")
                .add(R.id.frame_layout, searchFragment, "SEARCH_FRAGMENT")
                .add(R.id.frame_layout, favoriteFragment, "FAVORITE_FRAGMENT")
                .add(R.id.frame_layout, profileFragment, "PROFILE_FRAGMENT")
                .hide(searchFragment)
                .hide(favoriteFragment)
                .hide(profileFragment)
                .commit();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.map) {
                replaceFragment(mapFragment);
            } else if (itemId == R.id.search) {
                replaceFragment(searchFragment);
            } else if (itemId == R.id.favorite) {
                replaceFragment(favoriteFragment);
            } else if (itemId == R.id.profile) {
                replaceFragment(profileFragment);
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .hide(mapFragment)
                .hide(searchFragment)
                .hide(favoriteFragment)
                .hide(profileFragment)
                .show(fragment)
                .commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
