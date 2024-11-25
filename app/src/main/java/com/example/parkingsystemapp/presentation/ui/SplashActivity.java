package com.example.parkingsystemapp.presentation.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parkingsystemapp.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            ImageView splashLogo = findViewById(R.id.logo);
            Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            splashLogo.startAnimation(fadeIn);
            startActivity(new Intent(this, FirstPageActivity.class));
            finish();
        }, 1000);
    }
}