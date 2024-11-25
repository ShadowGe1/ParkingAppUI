package com.example.parkingsystemapp.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parkingsystemapp.R;

public class FirstPageActivity extends AppCompatActivity {
    TextView createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createAccount = findViewById(R.id.createAccount);

        createAccount.setOnClickListener(v -> {
            Intent intent = new Intent(FirstPageActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void startSignInActivity(View v) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }


}